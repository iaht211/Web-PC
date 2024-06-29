<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <!-- <link rel="stylesheet" href="/css/demo.css"> -->
            </head>
            <body>
                <div class="container mt-5">
                    <div class="row">
                        <div class="col-12 mx-auto">
                            <h3>Delete User With Id: ${user.id}</h3>
                            <form:form method="post" action="/admin/user/delete" modelAttribute="user">
                                <div class="mb-3" style="display: none;">
                                    <form:input type="text" class="form-control" path="id" />
                                </div>
                                <hr />
                                <div class="alert alert-danger" role="alert">
                                    Are you sure want delete this user !!!
                                </div>
                                <button type="submit" class="btn btn-danger">Submit</button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </body>
            </html>