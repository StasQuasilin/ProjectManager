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
        goalMembers.owner = ${goal.title.owner.toJson()}
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
    <c:set var="editable" value="${goal.title.owner.id eq user.id}"/>
    <div id="memberList">
        <div style="width: 280pt; max-height: 200pt; overflow-y: scroll">
            <div>
                {{owner.surname}} {{owner.forename}} ( <fmt:message key="owner"/> )
            </div>
            <div v-for="friend in friends">
                <c:if test="${editable}">
                    <input :id="friend.id" type="checkbox" v-model="friend.member">
                </c:if>
                <label :for="friend.id">
                    {{friend.surname}} {{friend.forename}}
                </label>
            </div>
        </div>
        <div class="modal-buttons">
            <button onclick="closeModal()">
                <fmt:message key="button.close"/>
            </button>
            <c:if test="${editable}">
                <button v-on:click="save()">
                    <fmt:message key="button.save"/>
                </button>
            </c:if>
        </div>
    </div>
</html>
