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
    document.getElementById("tasks-title").textContent = "Tasks: " + name;

    const res = await fetch("/api/projects/" + projectId + "/tasks");
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
        li.textContent = task.title + " [" + task.priority + "] - " + task.status;
        li.className = task.status;   // TODO / DOING / DONE, khớp với style.css
        list.appendChild(li);
    });
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
        // {"name": "..."} (validation) hoặc {"error": "..."} (lỗi khác)
        alert(Object.values(data).join(", "));
        return;
    }

    input.value = "";
    loadProjects();
};

loadProjects();