<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<div id="titleContent" class="title-content">
    <button onclick="loadModal('${goalEdit}')">
        <fmt:message key="button.goal.add"/>
    </button>
</div>