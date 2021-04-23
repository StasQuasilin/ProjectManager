<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 06.07.2020
  Time: 14:24
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="${context}/css/datePicker.css?v=${now}">
<link rel="stylesheet" href="${context}/css/calendar/calendar.css?v=${now}">
<script type="application/javascript" src="${context}/vue/pathBuilder.vue?v=${now}"></script>
<script type="application/javascript" src="${context}/vue/weekNumber.vue?v=${now}"></script>
<script type="application/javascript" src="${context}/vue/templates/calendar.vue?v=${now}"></script>
<script type="application/javascript" src="${context}/vue/templates/datetime/datePicker.vue?v=${now}"></script>
<script src="${context}/vue/calendar/calendarFreeItem.vue"></script>
<script type="application/javascript" src="${context}/vue/calendarPage.vue?v=${now}"></script>
<script>
  calendar.api.edit = '${edit}';
  calendar.api.remove = '${remove}';
  calendar.api.getCalendar = '${getCalendar}';
  calendar.api.randomTask = '${randomTask}';
  calendar.getCalendarData();
  subscriber.subscribe('${taskSubscribe}', calendar.handler);
  subscriber.subscribe('${subscribe}', calendar.calendarHandler);
</script>
<div id="calendar" class="full-size">
  <div class="left-panel">
    <div class="panel-header">
      <div class="panel-header-content">
        <fmt:message key="calendar.today"/>
        {{new Date(date).toLocaleDateString().substring(0, 5)}}
        <span class="text-button" style="float: right" v-on:click="edit()">
          <fmt:message key="button.add"/>
        </span>
      </div>
    </div>
    <div class="panel-content">
      <div class="item-container">
        <calendar-free-item v-for="item in todayItems()" :item="item" v-on:click.native="edit(item.id)"></calendar-free-item>
      </div>
    </div>
  </div>
  <div class="central-panel">
    <div class="panel-header">
      <div class="panel-header-content">
        <span class="text-button" v-on:click="switchDate(-1)">
          &#9204;
        </span>
        <date-picker :date="date" :props="props"></date-picker>
        <span class="text-button" v-on:click="switchDate(1)">
          &#9205;
        </span>
        <span class="text-button" v-if="new Date(date).toLocaleDateString() !== new Date().toLocaleDateString()"
              v-on:click="resetDate()" style="position: absolute; right: 0">
          <fmt:message key="date.today"/>
        </span>
        <span class="text-button" style="float: right" v-on:click="taskRandomizer()">
          RDM
        </span>
      </div>
    </div>
    <div class="panel-content">
      <div class="item-container">
        <div v-for="item in getCalendarItems()" v-on:click="edit(item.id)" style="display: flex; border: dotted white 1pt" :style="{'min-height': (item.length)+ 'pt'}"
             class="calendar-item" :class="{'filled' : item.title}">
<%--          Date-Time--%>
          <div style="padding-right: 5pt">
            {{item.time.substring(0, 5)}}
            <template v-if="item.length">
              +{{item.length}} min
            </template>
          </div>
<%--  Path--%>
          <div>
            <div v-if="item.parent" style="font-size: 10pt">
              <span v-for="p in item.parent.path">
                {{p.title}} /
              </span>
              <span>
                {{item.parent.title}}
              </span>
            </div>
            <div v-if="item.title">
              {{item.title}}
            </div>
          </div>
<%--  Menu--%>
          <div style="float: right">
            !!!!
          </div>
<%--          <div style="font-size: 10px">--%>
<%--            <div v-for="(v, k) in item">--%>
<%--              {{k}}: {{v}}--%>
<%--            </div>--%>
<%--          </div>--%>
        </div>
      </div>
    </div>
  </div>
  <div class="right-panel">
    <div class="panel-header">
      <div class="panel-header-content">
        <fmt:message key="calendar.active"/>
      </div>
    </div>
    <div class="panel-content">
      <div class="item-container">
        <calendar-free-item v-for="item in getItems()" :item="item" v-on:click.native="createItem(item.header)"></calendar-free-item>
      </div>
    </div>
  </div>
</div>
