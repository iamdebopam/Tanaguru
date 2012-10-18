<%@taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="compress" %>
<compress:html>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<c:choose>
    <c:when test="${fn:contains(pageContext.response.locale, '_')}">
        <c:set var="lang">
            ${fn:substringBefore(pageContext.response.locale, "_")}
        </c:set>
    </c:when>
    <c:otherwise>
        <c:set var="lang" value="${pageContext.response.locale}"/>
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${not empty configProperties['cdnUrl']}">
        <c:set var="warningImgUrl" value="${pageContext.request.scheme}://${configProperties['cdnUrl']}/Images/warning.jpg"/>
        <c:set var="creativeCommonLogoUrl" value="${pageContext.request.scheme}://${configProperties['cdnUrl']}/Images/creative_common_logo.png"/>
    </c:when>
    <c:otherwise>
        <c:set var="warningImgUrl">
            <c:url value="/Images/warning.jpg"/>  
        </c:set>
        <c:set var="creativeCommonLogoUrl">
            <c:url value="/Images/creative_common_logo.png"/>
        </c:set>
    </c:otherwise>
</c:choose>
<html lang="${lang}">
    <c:set var="pageTitle" scope="page">
        <fmt:message key="delete-user.pageTitle">
            <fmt:param>
            ${userNameToDelete}
            </fmt:param>
        </fmt:message>
    </c:set>
    <%@include file="template/head.jsp" %>
    <body id="tgm-home">
        <%@include file="template/header-utils.jsp" %>
        <div class="container">
            <c:set var="pageName" scope="page">
                <fmt:message key="delete-user.h1">
                    <fmt:param>
                    ${userNameToDelete}
                    </fmt:param>
                </fmt:message>
            </c:set>
            <ul class="breadcrumb">
                <li><a href="<c:url value="/home.html"/>"><fmt:message key="home.h1"/></a> <span class="divider"></span></li>
                <li><a href="<c:url value="/admin.html"/>"><fmt:message key="admin.h1"/></a> <span class="divider"></span></li>
                <li class="active">${pageName}</li>
            </ul>
            <div class="row">
                <div class="span16">
                    <h1>${pageName}</h1>
                </div>
            </div>
            <div class="row">
                <div class="span14 offset1 alert-message block-message warning">
                    <fmt:message key="delete-user.deletion-msg">
                        <fmt:param>${userNameToDelete}</fmt:param>
                    </fmt:message>
                    <div class="alert-actions">
                        <form method="post">
                            <input type="submit" value="<fmt:message key="delete-user.confirm-deletion"/>"/>
                        </form>
                    </div><!-- class="alert-actions"-->
                </div>
            </div><!-- class="row"-->
            <div class="row">
                <div class="span16 main-logo">
                    <img src="${warningImgUrl}" alt=""/>
                </div><!-- class="span16 main-logo" -->
            </div><!-- class="row" -->
            <div class="row">
                <div class="span4 offset10">
                    <a title="Creative Commons Attribution 3.0 License" href="http://creativecommons.org/licenses/by/3.0/">
                        <img src="${creativeCommonLogoUrl}" alt="License"/>
                    </a>
                    <a title="Flickr: Galerie de Patrick van IJzendoorn" href="http://www.flickr.com/photos/sleepishly/">Jessica Diamond</a>
                </div><!-- class="span4 offset9" -->
            </div><!-- class="row" -->
        </div><!-- class="container"-->                    
    <%@include file="template/footer.jsp" %>
    </body>
</html>
</compress:html>