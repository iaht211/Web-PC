<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                                <h1 class="mt-4">User</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Orders</li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-xxl-12 mx-auto">
                                            <div class="d-flex justify-content-between">
                                                <h3>Table Orders</h3>
                                            </div>

                                            <table class="table" modelAttribute="users">
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Total Price</th>
                                                        <th>User</th>
                                                        <th>Status</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="order" items="${orders}">
                                                        <tr>
                                                            <th scope="row">${order.id}</th>
                                                            <td>
                                                                <fmt:formatNumber value="${order.totalPrice}"
                                                                    type="number" /> vnd
                                                            </td>
                                                            <td>${order.user.fullName}</td>
                                                            <td>
                                                                <a href="/admin/order/${order.id}"
                                                                    class="btn btn-success">View</a>
                                                                <a href="/admin/order/update/${order.id}"
                                                                    class="btn btn-warning">Update</a>
                                                                <a href="/admin/order/delete/${order.id}"
                                                                    class="btn btn-danger">Delete</a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <nav aria-label="Page navigation example">
                                                <ul class="pagination justify-content-center">
                                                  <li class="page-item">
                                                    <a class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}" href="order?page=${currentPage-1}" aria-label="Previous">
                                                      <span aria-hidden="true">&laquo;</span>
                                                    </a>
                                                  </li>
                                                  <c:forEach begin="0" end="${totalPages - 1}" varStatus="loop">
                                                  <li class="page-item"><a class="page-link" href="order?page=${loop.index+1}">${loop.index + 1}</a></li>
                                                </c:forEach>
                                                  <li class="page-item">
                                                    <a class="${totalPages eq currentPage ? 'disabled page-link' : 'page-link'}" href="order?page=${currentPage+1}" aria-label="Next">
                                                      <span aria-hidden="true">&raquo;</span>
                                                    </a>
                                                  </li>
                                                </ul>
                                              </nav>
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