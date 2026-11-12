let currentProjectId = null;
let currentProjectName = "";

async function loadProjects() {
    const res = await fetch("/api/projects");
    if (!res.ok) {
        alert("Could not load projects");
        return;
    }
    const projects = await res.json();
    const list = document.getElementById("project-list");
    list.innerHTML = "";
    projects.forEach(p => {
        const li = document.createElement("li");
        li.textContent = p.name;
        li.onclick = () => loadTasks(p.id, p.name);
        list.appendChild(li);
    });
}

async function loadTasks(projectId, name) {
    currentProjectId = projectId;
    currentProjectName = name;

    document.getElementById("tasks-title").textContent = "Tasks: " + name;
    document.getElementById("task-form").hidden = false;

    const res = await fetch(`/api/projects/${projectId}/tasks`);
    if (!res.ok) {
        alert("Could not load tasks");
        return;
    }
    const tasks = await res.json();

    const list = document.getElementById("task-list");
    list.innerHTML = "";

    if (tasks.length === 0) {
        const li = document.createElement("li");
        li.textContent = "No tasks yet";
        list.appendChild(li);
        return;
    }

    tasks.forEach(task => {
        const li = document.createElement("li");
        li.className = task.status; // TODO / DOING / DONE

        const text = document.createElement("span");
        text.textContent = task.title + " [" + task.priority + "] - " + task.status;

        const actions = document.createElement("span");
        actions.className = "actions";

        const nextBtn = document.createElement("button");
        nextBtn.textContent = "Next status";
        nextBtn.style.marginRight = "10px";

        nextBtn.onclick = () => nextStatus(task);

        const delBtn = document.createElement("button");
        delBtn.textContent = "Delete task";
        delBtn.title = "Delete task";
        delBtn.className = "delete";
        delBtn.onclick = () => {
            if (confirm(`Delete "${task.title}"?`)) deleteTask(task);
        };

        actions.appendChild(nextBtn);
        actions.appendChild(delBtn);
        li.appendChild(text);
        li.appendChild(actions);
        list.appendChild(li);
    });
}

async function nextStatus(task) {
    const res = await fetch(`/api/tasks/${task.id}/advance`, { method: "POST" });

    if (!res.ok) {
        const err = await res.json();
        document.getElementById("error").textContent = Object.values(err).join(", ");
        return;
    }
    document.getElementById("error").textContent = "";
    loadTasks(currentProjectId, currentProjectName);
}

async function deleteTask(task) {
    const res = await fetch(`/api/tasks/${task.id}`, { method: "DELETE" });

    if (!res.ok) {
        const err = await res.json();
        document.getElementById("error").textContent = Object.values(err).join(", ");
        return;
    }
    document.getElementById("error").textContent = "";
    loadTasks(currentProjectId, currentProjectName);
}

document.getElementById("add-project").onclick = async () => {
    const input = document.getElementById("project-name");
    const name = input.value.trim();

    const res = await fetch("/api/projects", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name: name })
    });

    if (!res.ok) {
        const data = await res.json();
        alert(Object.values(data).join(", "));
        return;
    }

    input.value = "";
    loadProjects();
};

document.getElementById("add-task").onclick = async () => {
    if (currentProjectId === null) return;

    const body = {
        title: document.getElementById("task-title").value,
        priority: document.getElementById("task-priority").value,
        dueDate: document.getElementById("task-due").value || null
    };
    const res = await fetch(`/api/projects/${currentProjectId}/tasks`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    });
    if (!res.ok) {
        const err = await res.json();
        document.getElementById("error").textContent = Object.values(err).join(", ");
        return;
    }
    document.getElementById("error").textContent = "";
    document.getElementById("task-title").value = "";
    document.getElementById("task-due").value = "";
    loadTasks(currentProjectId, currentProjectName);
};

loadProjects();