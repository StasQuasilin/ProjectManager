<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<div id="titleContent" class="title-content">
    <button onclick="loadModal('${edit}')">
        <fmt:message key="button.goal.add"/>
    </button>
</div>