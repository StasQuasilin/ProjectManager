<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<div id="titleContent" class="title-content">
    <button onclick="loadModal('${edit}')">
        <fmt:message key="button.goal.add"/>
    </button>
</div>