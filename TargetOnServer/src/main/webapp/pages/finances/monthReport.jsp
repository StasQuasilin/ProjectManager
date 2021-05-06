
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 2021-03-16
  Time: 09:11
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/finances/monthReport.vue?v=${now}"></script>
<script>
    report.api.build = '${reportBuildApi}';
    report.buildReport();
</script>
<div id="reportView">
    <div style="width: 100%; text-align: center">
        <span class="text-button" v-on:click="changeMonth(-1)">
            &#9664;
        </span>
        <span>
            {{date.toLocaleDateString().substring(3)}}
        </span>
        <span class="text-button" v-on:click="changeMonth(1)">
            &#9654;
        </span>
    </div>
    <div style="overflow: scroll; max-height: 200pt">
        <div v-for="(v, k) in result" style="border-bottom: dashed gray 1pt">
            <span>
                {{(k + 1).toLocaleString()}}:
            </span>
            <span style="width: 100pt; display: inline-block">
                {{v.title}}
            </span>
            <span style="display: inline-block; width: 50pt">
                +{{v.plus.toLocaleString()}}
            </span>
            <span style="display: inline-block; width: 50pt">
                {{v.minus.toLocaleString()}}
            </span>
            <span style="display: inline-block; width: 50pt">
                = {{(v.plus+v.minus).toLocaleString()}}
            </span>
        </div>
    </div>
    <div style="width: 100%; text-align: center">
        <button onclick="closeModal()">
            <fmt:message key="button.close"/>
        </button>
    </div>
</div>
</html>
