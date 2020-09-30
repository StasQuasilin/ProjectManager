
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 29.09.20
  Time: 16:11
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
    <link rel="stylesheet" href="${context}/css/friendList.css">
    <script type="text/javascript" src="${context}/vue/friends/friendshipReq.vue"></script>
    <script type="text/javascript" src="${context}/vue/friends/friendList.vue"></script>
    <script type="text/javascript">
        friendsList.api.findUser = '${findUser}';
        subscriber.subscribe('${friendshipReqSubscribe}', friendshipReq.handler);
        subscriber.subscribe('${friendsSubscribe}', friendsList.handler);
    </script>
    <div class="item-container">
        <div id="friendshipReq">

        </div>
        <div id="friends" class="friend-list">
            <div  id="titleContent" style="display: inline-block">
                <c:set var="searchPlaceholder"><fmt:message key="friend.placeholder"/></c:set>
                <input  type="search" placeholder="${searchPlaceholder}" v-model="searchText" v-on:keyup="searchUser()">
            </div>
            <div v-for="friend in getItems()" class="friend-tile">
                <div>

                </div>
                <div>
                    <div>
                        {{friend.surname}}
                    </div>
                    <div>
                        {{friend.forename}}
                    </div>
                </div>
            </div>
        </div>
    </div>

</html>
