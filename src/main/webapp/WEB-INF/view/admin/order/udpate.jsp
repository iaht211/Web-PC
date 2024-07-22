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
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Dashboard</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active"><a href="/admin/product">Products</a></li>
                                    <li class="breadcrumb-item"><a>Update</a></li>
                                </ol>
                                <div class="container mt-2">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Update Product</h3>
                                            update order
                                            <!-- <form:form class="row g-3 needs-validation" method="post"
                                                action="/admin/product/update" modelAttribute="product"
                                                enctype="multipart/form-data">
                                                <div class="mb-3" style="display: none;">
                                                    <form:input type="text" class="form-control" path="id" />
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Name</label>
                                                    <form:input type="text" class="form-control" path="name" />
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Price</label>
                                                    <form:input type="text" class="form-control" path="price" />
                                                </div>
                                                <div class="col-md-12">
                                                    <label class="form-label">Details description</label>
                                                    <form:input type="text" class="form-control" path="detail_desc" />
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Short description</label>
                                                    <form:input type="text" class="form-control" path="short_desc" />
                                                </div>
                                                <div data-mdb-input-init class="form-outline col-md-6">
                                                    <label class="form-label">Quantity</label>
                                                    <form:input type="number" class="form-control form-icon-trailing"
                                                        path="quantity" />
                                                </div>
                                                <div class="col-md-6">
                                                    <label for="validationCustom05" class="form-label">Factory</label>
                                                    <form:select class="form-select" aria-label="Default select example"
                                                        path="factory">
                                                        <form:option value="Apple">Apple</form:option>
                                                        <form:option value="one">One</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="col-md-6">
                                                    <label for="validationCustom05" class="form-label">Factory</label>
                                                    <form:select class="form-select" aria-label="Default select example"
                                                        path="target">
                                                        <form:option value="Gaming">Gaming</form:option>
                                                        <form:option value="1">One</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="col-md-6">
                                                    <label for="avatarFile" class="form-label">Avatar:</label>
                                                    <input id="avatarFile" class="form-control" type="file"
                                                        accept=".png, .jpg, .jpeg" name="file" />
                                                </div>
                                                <div class="col-12">
                                                    <img id="avatarPreview" style="max-height: 250px; display: none"
                                                        alt="avatar preview" />
                                                </div>
                                                <div class="col-12">
                                                    <button class="btn btn-primary" type="submit">Submit form</button>
                                                </div>
                                            </form:form> -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
            </body>
            </html>