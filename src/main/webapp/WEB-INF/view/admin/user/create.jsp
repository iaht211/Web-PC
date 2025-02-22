<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                <meta name="author" content="Hỏi Dân IT" />
                <title>Dashboard - Hỏi Dân IT</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Create user</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active"><a href="/admin/user">Users</a></li>
                                    <li class="breadcrumb-item"><a>Create</a></li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Create User</h3>
                                            <form:form class="row g-3" method="post" action="/admin/user/create"
                                                modelAttribute="newUser" enctype="multipart/form-data">
                                                <div class="col-md-6">
                                                    <c:set var="errorEmail">
                                                        <form:errors path="email" cssClass="invalid-feedback" />
                                                    </c:set>
                                                    <label class="form-label">Email</label>
                                                    <form:input type="email"
                                                        class="form-control ${not empty errorEmail ? 'is-invalid' : ''}"
                                                        path="email" />
                                                    ${errorEmail}
                                                </div>
                                                <div class="col-md-6">
                                                    <c:set var="errorPassword">
                                                        <form:errors path="password" cssClass="invalid-feedback" />
                                                    </c:set>
                                                    <label class="form-label">Password</label>
                                                    <form:input type="password"
                                                        class="form-control ${not empty errorPassword ? 'is-invalid' : ''}"
                                                        path="password" />
                                                    ${errorPassword}
                                                </div>
                                                <div class="col-md-6">
                                                    <label for="inputEmail4" class="form-label">Phone number:</label>
                                                    <form:input type="text" class="form-control" path="phone" />
                                                </div>
                                                <div class="col-md-6">
                                                    <c:set var="errorName">
                                                        <form:errors path="fullName" cssClass="invalid-feedback" />
                                                    </c:set>
                                                    <label for="inputPassword4" class="form-label">Full name:</label>
                                                    <form:input type="password"
                                                        class="form-control ${not empty errorName ? 'is-invalid' : ''}"
                                                        path="fullName" />
                                                    ${errorName}
                                                </div>
                                                <div class="col-12">
                                                    <label for="inputAddress" class="form-label">Address</label>
                                                    <form:input type="text" class="form-control" path="address"
                                                        placeholder="1234 Main St" />
                                                </div>
                                                <div class="col-md-6">
                                                    <label for="inputState" class="form-label">Role:</label>
                                                    <form:select class="form-select" path="role.name">
                                                        <form:option value="ADMIN">ADMIN</form:option>
                                                        <form:option value="USER">USER</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="col-md-6">
                                                    <label for="avatarFile" class="form-label">Avatar:</label>
                                                    <input id="avatarFile" class="form-control" type="file"
                                                        accept=".png, .jpg, .jpeg" name="file">
                                                </div>
                                                <div class="col-12 mb-3">
                                                    <img id="avatarPreview" style="max-height: 250px; display: none"
                                                        alt="avatar preview" />
                                                </div>
                                                <div class="col-md-12">
                                                    <button type="submit" class="btn btn-primary">Submit</button>
                                                </div>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });
                </script>
            </body>

            </html>