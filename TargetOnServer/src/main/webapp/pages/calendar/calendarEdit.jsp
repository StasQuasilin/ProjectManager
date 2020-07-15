<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 15.07.20
  Time: 10:23
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${context}/vue/calendarEdit.vue"></script>
<script>
    calendarEdit.api.save = '${save}';
</script>
<table id="calendarEdit">
    <tr>
        <td colspan="2">
            <label for="title">
                <fmt:message key="calendar.title"/>
            </label>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <input id="title" v-model="calendarItem.title" autocomplete="off" onfocus="this.select()">
        </td>
    </tr>
    <tr v-if="!calendarItem.useDate">
        <td colspan="2" style="text-align: right">
            <span class="text-button" v-on:click="calendarItem.useDate = true">
                <fmt:message key="task.date.add"/>
            </span>
        </td>
    </tr>
    <template v-else>
        <tr>
            <td>
                <fmt:message key="task.date"/>
            </td>
            <td>
                <span>
                    {{new Date(calendarItem.date).toLocaleDateString()}}
                </span>
                <span class="text-button" v-on:click="calendarItem.useDate = false">
                    &times;
                </span>
            </td>
        </tr>
        <tr v-if="!calendarItem.useTime">
            <td colspan="2" style="text-align: right">
                <span class="text-button" v-on:click="calendarItem.useTime = true">
                    <fmt:message key="task.time.add"/>
                </span>
            </td>
        </tr>
        <tr v-else>
            <td>
                <fmt:message key="task.time"/>
            </td>
            <td>
                <span class="text-button">
                    {{new Date(calendarItem.date).toLocaleTimeString()}}
                </span>
                <span class="text-button" v-on:click="calendarItem.useTime = false">
                    &times;
                </span>
            </td>
        </tr>
    </template>
    <tr>
        <td>
            <fmt:message key="task.length"/>
        </td>
        <td>
            1:00
        </td>
    </tr>
    <tr>
        <td>
            <label for="repeat">
                <fmt:message key="task.repeat"/>
            </label>
        </td>
        <td>
            <select id="repeat" v-model="calendarItem.repeat">
                <option v-for="r in repeats">
                    {{r}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td colspan="2" style="text-align: center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save">
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>
</table>
