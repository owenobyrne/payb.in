<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        <p>Access could not be granted. (<%= ((AuthenticationException) session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>)</p>
      </div>
    </c:if>
    <c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>

    <authz:authorize ifAllGranted="USER">
      <h2>Enter Secrets</h2>

      <p>To enable "<c:out value="${consumer.consumerName}"/>" to process authorisations and refunds, 
      you will need to provide your shared secret and refund secret. 
      <b>These are never stored on the Payb.in servers and are stored as encrypted data on your application/device.</b></p>

      <form method="post" action="<c:url value="/oauth/confirm_secrets"/>">
        <p><label>Shared Secret: <input type='text' name='j_sharedsecret' value="secret"/></label></p>
        <p><label>Refund Secret: <input type='text' name='j_refundpassword' value="refund"/></label></p>
        
        <input name="oauth_token" value="<c:out value="${oauth_token}"/>" type="hidden"/>
        <c:if test="${!empty oauth_callback}">
        <input name="callbackURL" value="<c:out value="${oauth_callback}"/>" type="hidden"/>
        </c:if>
        <label><input name="authorize" value="Save" type="submit"/></label>
      </form>
    </authz:authorize>
  </div>


</body>
</html>
