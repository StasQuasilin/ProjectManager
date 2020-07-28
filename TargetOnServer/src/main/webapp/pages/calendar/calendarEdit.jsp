<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 15.07.20
  Time: 10:23
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="${context}/css/timePicker.css">
<script src="${context}/vue/templates/datetime/datePicker.vue"></script>
<script src="${context}/vue/templates/datetime/timePicker.vue"></script>
<script src="${context}/vue/templates/inputWithSearch.vue"></script>
<script src="${context}/vue/calendarEdit.vue"></script>
<script>
    calendarEdit.api.save = '${save}';
    calendarEdit.props.findCategory = '${findCategory}';

    <c:if test="${not empty item}">
    calendarEdit.calendarItem = ${item.toJson()};
    </c:if>
    now = new Date();
    if (calendarEdit.calendarItem.date){
        calendarEdit.useDate = true;
        if (calendarEdit.calendarItem.time){
            calendarEdit.useTime = true;
        } else {
            calendarEdit.calendarItem.time = now.toLocaleTimeString()
        }
    } else {
        calendarEdit.calendarItem.date = now.toISOString().substring(0, 10);
        calendarEdit.calendarItem.time = now.toLocaleTimeString().substring(0, 5);
    }
</script>
<table id="calendarEdit">
    <tr>
        <td colspan="2">
            <fmt:message key="calendar.title"/>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <input-search :object="calendarItem.category" :props="props"></input-search>
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
                <date-picker :date="calendarItem.date" :props="dateProps"></date-picker>
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
                <time-picker :time="calendarItem.time" :props="timeProps"></time-picker>
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
<%--            <vue-timepicker></vue-timepicker>--%>
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
