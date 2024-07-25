<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


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
                                    <li class="breadcrumb-item active"><a href="/admin/order">Orders</a></li>
                                    <li class="breadcrumb-item"><a>Update</a></li>
                                </ol>
                                <div class="container mt-2">
                                    <div class="row">
                                        <div class="col-8 mx-auto">
                                            <h3>Update Product</h3>
                                            <form:form class="row g-3 needs-validation" method="post"
                                            action="/admin/order/update" modelAttribute="order"
                                            enctype="multipart/form-data">
                                            <div class="card">
                                                <div class="card-body">
                                                  <h6 class="card-title text-muted">Order id: ${order.id}</h6>
                                                  <form:input type="text" class="form-control" path="id" style="display: none;" />
                                                  <h6 class="card-subtitle mb-2 text-muted">Price: 
                                                    <fmt:formatNumber type="number" value="${order.totalPrice}" /> đ
                                                  </h6>
                                                  <p class="card-text"></p>
                                                  <div class="col-md-6">
                                                    <label for="validationCustom05" class="form-label">User: </label>
                                                    <form:input type="text" class="form-control" path="receiverName" />
                                                </div>
                                                  <div class="col-md-6">
                                                    <label for="validationCustom05" class="form-label">Status</label>
                                                    <form:select class="form-select" aria-label="Default select example"
                                                        path="status">
                                                        <form:option value="PENDING">PENDING</form:option>
                                                        <form:option value="COMPLETE">COMPLETE</form:option>
                                                    </form:select>
                                                    </div>           
                                                </div>
                                              </div>
                                              <div class="col-12">
                                                <button class="btn btn-primary" type="submit">Submit form</button>
                                            </div>
                                            </form:form>
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