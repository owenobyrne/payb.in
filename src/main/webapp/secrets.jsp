<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<authz:authorize ifAllGranted="ROLE_USER">
  <c:redirect url="index.jsp"/>
</authz:authorize>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
  <title>Payb.in</title>
  <link type="text/css" rel="stylesheet" href="<c:url value="/style.css"/>"/>
</head>

<body>

  <h1>Payb.in</h1>

  <div id="content">
    <c:if test="${!empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
      <div class="error">
		<h2>Woops!</h2>

      	<p>Your login attempt was not successful. (<%= ((AuthenticationException) session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>)</p>
      </div>
    </c:if>
    <c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>

    <authz:authorize ifNotGranted="ROLE_USER">
      <h2>Permissions</h2>

      <p>To  process authorisations and refunds, 
      you will need to provide your shared secret and refund secret. 
      <b>These are never stored on the Payb.in servers and are stored as encrypted data on your application/device.</b></p>

      <form method="get" action="<c:url value="/oauth/authorize"/>">
        <p><label>Shared Secret: <input type='text' name='j_secret' value="secret"></label></p>
        <p><label>Refund Secret: <input type='text' name='j_refund_secret' value="refund"></label></p>
        
        <input name="oauth_token" value="<c:out value="${oauth_token}"/>" type="hidden"/>
        <c:if test="${!empty oauth_callback}">
        <input name="callbackURL" value="<c:out value="${oauth_callback}"/>" type="hidden"/>
        </c:if>
        <label><input name="authorize" value="Authorize" type="submit"></label>
      </form>

      <p>Log in with your RealControl Company, Username and Password.</p>
      <form action="<c:url value="/secrets.jsp"/>" method="post">
        <p><label>Company: <input type='text' name='j_company' value="ccentre"></label></p>
        <p><label>Username: <input type='text' name='j_username' value="owen"></label></p>
        <p><label>Password: <input type='text' name='j_password' value="OeOyn616"></label></p>
        
        <p><input name="login" value="Login" type="submit"></p>
      </form>
    </authz:authorize>
  </div>


</body>
</html>
