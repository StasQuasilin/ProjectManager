<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <script>
        function subscribe(handler) {
            <c:forEach items="${subscribes}" var="subscribe">
            subscriber.subscribe('${subscribe}', handler.handle);
            </c:forEach>
            unsubscribe = function(){
                <c:forEach items="${subscribes}" var="subscribe">
                subscriber.unsubscribe('${subscribe}');
                </c:forEach>
            }
        }
    </script>
</html>