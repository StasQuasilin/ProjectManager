<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <script>
        function subscribe(handler) {
            <c:forEach items="${subscribes}" var="subscribe">
            subscriber.subscribe('${subscribe}', handler.handle);
            subscriber.unSubscribers.push('${subscribe}');
            </c:forEach>
        }
    </script>
</html>