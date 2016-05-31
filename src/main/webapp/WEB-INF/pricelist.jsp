<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 29.05.2016
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Price list</title>

</head>
<body>
<div>
    <form action="price" method="post">
        <input type="text" name="sku">
        <input type="submit" value="get Price">
    </form>
</div>

<div>
    <fmt:setLocale value="ru_RU"/>
    <table border="0">
        <tr>

            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td><input type="text" name="in_kr17" value="<fmt:formatNumber value="${stock.kr57UsdPrice}"/>" size="5">
            </td>
            <td><input type="text" name="in_kr57s" value="<fmt:formatNumber value="${stock.kr57UsdPrice}"/>" size="5">
            </td>
            <td><input type="text" name="in_prec" value="<fmt:formatNumber value="${stock.preciousUsdPrice}"/>"
                       size="5"></td>
            <td><input type="text" name="in_kr57b" value="<fmt:formatNumber value="${stock.kr57bigUsdPrice}"/>"
                       size="5"></td>
            <td><input type="text" name="in_gold" value="<fmt:formatNumber value="${stock.gold}"/>" size="5"></td>
            <td></td>
        </tr>
        <tr>
            <td>ID</td>
            <td>GROSS</td>
            <td>NET</td>
            <td>COUNT</td>
            <td>KR 17</td>
            <td>KR 57</td>
            <td>PREC</td>
            <td>KR 57 big</td>
            <td>costGOLD</td>
            <td>costWORK</td>
            <td>costSTONE</td>
            <td>costPrice</td>
        </tr>

        <c:forEach items="${skuList}" var="sku">
            <tr>
                <td>${sku.id}</td>
                <td><fmt:formatNumber value="${sku.w_gross}"/></td>
                <td><fmt:formatNumber value="${sku.w_net}"/></td>
                <td><fmt:formatNumber value="${sku.count_stone}"/></td>
                <td>
                    <c:if test="${sku.KR_17 gt 0}">
                        <fmt:formatNumber value="${sku.KR_17}"/>
                    </c:if>
                </td>
                <td>
                    <c:if test="${sku.KR_57s gt 0}">
                        <fmt:formatNumber value="${sku.KR_57s}"/>
                    </c:if>
                </td>
                <td>
                    <c:if test="${sku.precious gt 0}">
                        <fmt:formatNumber value="${sku.precious}"/>
                    </c:if>
                </td>
                <td>
                    <c:if test="${sku.KR_57b gt 0}">
                        <fmt:formatNumber value="${sku.KR_57b}"/>
                    </c:if>
                </td>
                <td>${sku.w_net*stock.gold}</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
