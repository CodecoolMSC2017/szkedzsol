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
        <c:url value="/profile.js" var="profileScriptUrl"/>
        <c:url value="/schedule.js" var="scheduleScriptUrl"/>
        <c:url value="/schedules.js" var="schedulesScriptUrl"/>
        <c:url value="/task.js" var="taskScriptUrl"/>
        <c:url value="/tasks.js" var="tasksScriptUrl"/>
        <c:url value="/back-to-profile.js" var="backToProfileScriptUrl"/>
        <c:url value="/logout.js" var="logoutScriptUrl"/>
        <link rel="stylesheet" type="text/css" href="${styleUrl}">
        <link rel="stylesheet" type="text/css" href="${indexUrl}">
        <script src="${indexScriptUrl}"></script>
        <script src="${registerScriptUrl}"></script>
        <script src="${loginScriptUrl}"></script>
        <script src="${profileScriptUrl}"></script>
        <script src="${scheduleScriptUrl}"></script>
        <script src="${schedulesScriptUrl}"></script>
        <script src="${taskScriptUrl}"></script>
        <script src="${tasksScriptUrl}"></script>
        <script src="${backToProfileScriptUrl}"></script>
        <script src="${logoutScriptUrl}"></script>
        <title>App</title>
    </head>
<body>
<div id="register-content" class="content">
    <h1>Register</h1>
    <form id="register-form" onsubmit="return false;">
        <p>Email</p>
        <input type="text" name="email">
        <p>Name</p>
        <input type="text" name="name">
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
        <input type="text" name="email">
        <p>Name</p>
        <input type="text" name="name">
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
    <h2>Links</h2>
    <ul>
        <li><a href="javascript:void(0);" onclick="onSchedulesClicked();">Schedules</a></li>
        <li><a href="javascript:void(0);" onclick="onTasksClicked();">Tasks</a></li>
    </ul>
</div>
<div id="schedules-content" class="hidden content">
    <h1>Schedules</h1>
    <table id="schedules">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <br>
    <form id="add-schedule-form" onsubmit="return false;">
    <h1>Add</h1>
    <p>Name</p>
    <input type="text" name="scheduleTitle"><br>
    <br>
    <button onclick="onScheduleAddClicked(); onSchedulesClicked();">Add New Schedule</button>
    </form>
</div>
<div id="schedule-form" class="hidden content">

</div>
<div id="tasks-content" class="hidden content">
    <h1>Tasks</h1>
    <table id="tasks">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <h2>Add new Task</h2>
    <form id="task-form" onsubmit="return false;">
        <p>Task Name</p>
        <input type="text" name="name">
        <p>Task Description</p>
        <input type="text" name="description">
        <button onclick="onTaskAddClicked();onTasksClicked();">Add</button>
    </form>
    <br>
</div>
<div id="task-content" class="hidden content">
    <h1>Coupon</h1>
    <p>ID: <span id="task-id"></span></p>
    <p>Name: <span id="task-name"></span></p>
    <p>Percentage: <span id="task-description"></span>%</p>
    <p>Shops: <span id="coupon-shops"></span></p>
    <h2>Add to shops</h2>
    <form id="coupon-shops-form" onsubmit="return false">
        <select name="shopIds" multiple>
        </select>
        <button onclick="onCouponShopsAddClicked();">Add</button>
    </form>
</div>
<div id="back-to-profile-content" class="hidden content">
    <button onclick="onBackToProfileClicked();">Back to profile</button>
</div>
<div id="logout-content" class="hidden content">
    <button id="logout-button">Logout</button>
</div>
</body>
</html>
