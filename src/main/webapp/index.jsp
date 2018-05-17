<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <c:url value="/style.css" var="styleUrl"/>
        <c:url value="/index.css" var="indexUrl"/>
        <c:url value="/index.js" var="indexScriptUrl"/>
        <c:url value="/register.js" var="registerScriptUrl"/>
        <c:url value="/login.js" var="loginScriptUrl"/>
        <c:url value="/menupoints.js" var="menuScriptUrl"/>
        <c:url value="/profile.js" var="profileScriptUrl"/>
        <c:url value="/schedule.js" var="scheduleScriptUrl"/>
        <c:url value="/schedules.js" var="schedulesScriptUrl"/>
        <c:url value="/task.js" var="taskScriptUrl"/>
        <c:url value="/tasks.js" var="tasksScriptUrl"/>

        <c:url value="/users.js" var="usersScriptUrl"/>

        <c:url value="/back-to-profile.js" var="backToProfileScriptUrl"/>
        <c:url value="/logout.js" var="logoutScriptUrl"/>
        <link rel="stylesheet" type="text/css" href="${styleUrl}">
        <link rel="stylesheet" type="text/css" href="${indexUrl}">
        <script src="${indexScriptUrl}"></script>
        <script src="${registerScriptUrl}"></script>
        <script src="${loginScriptUrl}"></script>
        <script src="${menuScriptUrl}"></script>
        <script src="${profileScriptUrl}"></script>
        <script src="${scheduleScriptUrl}"></script>
        <script src="${schedulesScriptUrl}"></script>
        <script src="${taskScriptUrl}"></script>
        <script src="${tasksScriptUrl}"></script>
        <script src="${usersScriptUrl}"></script>
        <script src="${backToProfileScriptUrl}"></script>
        <script src="${logoutScriptUrl}"></script>
        <title>App</title>
    </head>
<body>
<div id="register-content" class="content">
    <h1>Register</h1>
    <form id="register-form" onsubmit="return false;">
        <p>Email</p>
        <input type="text" name="email" required>
        <p>Name</p>
        <input type="text" name="name" required>
        <br><br>
        <button id="register-button" class="mainButton">Sign up</button>
    </form>
    <br>
    <button id="tologin-button" class="mainButton">Already registered</button>
</div>
<div id="login-content" class="hidden content">
    <h1>Login</h1>
    <form id="login-form" onsubmit="return false;">
        <p>Email</p>
        <input type="text" name="email" required>
        <p>Name</p>
        <input type="text" name="name" required>
        <br><br>
        <button id="login-button" class="mainButton">Sign in</button>
    </form>
    <br>
    <button id="toregister-button" class="mainButton">Sign up</button>
</div>
<div id="profile-content" class="hidden content">
    <h1>Profile</h1>
    <p>Email: <span id="user-email"></span></p>
    <p>Name: <span id="user-name"></span></p>
</div>
<div id="menu">
</div>

<div id="schedules-content" class="hidden content">
    <h1>Schedules</h1>
    <table id="schedules">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <br>
    <form id="add-schedule-form" onsubmit="return false;">
    <input type="text" name="scheduleTitle" placeholder="Schedule name" required><br>
    <button onclick="onScheduleAddClicked(); onSchedulesClicked();">Add New Schedule</button>
    </form>
</div>
<div id="schedule-content" class="hidden content">
    <h1>Schedule</h1>
    <p>ID: <span id="schedule-id"></span></p>
    <p>Name: <span id="schedule-name"></span></p>
    <br><br>
    <form id="how-many" onsubmit="return false;">
    <p>How many days do you need in your schedule?</p>
    <input type="number" min="0" max="7" name="howMany">
    <button onclick="onHowManyColClicked();">Add Days</button>
    </form>
</div>

<div id="schedule-tasks-content" class="hidden content">
    <button id="load-tasks">Load</button>
    <div id="task-list-drop"></div><br><br>
    <br>
    <button float:right onclick="readDropZone()">Get Object Data</button>
    <button float:right onclick="clearDropZone()">Clear</button>
</div>

<div id="tasks-content" class="hidden content">
    <h1>Tasks</h1>
    <table id="tasks">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <br><br><br><br><br><br><br><br>
    <form id="task-form" onsubmit="return false;">
        <h2>Add new Task</h2>
        <p>Task Name</p>
        <input type="text" name="name" required>
        <p>Task Description</p>
        <input type="text" name="description" required>
        <button onclick="onTaskAddClicked();onTasksClicked();">Add</button>
    </form>
    <br>
</div>
<div id="task-content" class="hidden content">
    <h1>Task</h1>
    <p>ID: <span id="task-id"></span></p>
    <p>Name: <span id="task-name"></span></p>
    <p>Description: <span id="task-description"></span></p>
    <form id="modify-task-form" onsubmit="return false;">
        <input type="text" name="name" placeholder="Task name">
        <input type="text" name="description" placeholder="Task description">
        <button onclick="onTaskModifyClicked();">Modify</button>
    </form>
</div>

<div id="users-content" class="hidden content">
    <h1>Registered users:</h1>
    <button onclick="onUsersClicked();">Registered Users</button>
    <table id="users">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
<div id="back-to-profile-content" class="hidden content">
    <br>
    <button onclick="onBackToProfileClicked();">Back to profile</button>
</div>


<div id="logout-content" class="hidden content">
    <button id="logout-button">Logout</button>
</div>
</body>
</html>
