<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 30.09.20
  Time: 10:38
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script src="${context}/vue/goals/goalMembers.vue"></script>
    <script>
        goalMembers.api.save = '${save}';
        goalMembers.item = ${item};
        goalMembers.owner = ${user.toJson()}
        friends = {};
        <c:forEach items="${friends}" var="friend">
        friend = ${friend.toJson()};
        if (friend) {
            friends[friend.id] = friend;
        }
        </c:forEach>
        <c:forEach items="${members}" var="member">
        member = ${member.member.toJson()};
        if (member){
            mId = member.id;
            if (!friends[mId]){
                friends[mId] = member;
            }
            friends[mId].member = true;
        }
        </c:forEach>
            goalMembers.friends = (Object.values(friends));
    </script>
    <div id="memberList">
        <div>
            {{owner.surname}} {{owner.forename}} ( <fmt:message key="owner"/> )
        </div>
        <div v-for="friend in friends">
            <input :id="friend.id" type="checkbox" v-model="friend.member">
            <label :for="friend.id">
                {{friend.surname}} {{friend.forename}}
            </label>
        </div>
        <div>
            <button v-on:click="save()">
                <fmt:message key="button.save"/>
            </button>
        </div>
    </div>
</html>
