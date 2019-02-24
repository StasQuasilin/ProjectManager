<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ru"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.2.3/jquery.min.js"></script>--%>
  <script src="${context}/js/projects/projectEdit.js"></script>
  <link rel="stylesheet" type="text/css" href="${context}/datetimepicker/jquery.datetimepicker.css"/>
  <script src="${context}/datetimepicker/jquery.js"></script>
  <script src="${context}/datetimepicker/build/jquery.datetimepicker.full.min.js"></script>
  <script type="text/javascript">
    $(function(){
      $.datetimepicker.setLocale('${language}');
      $('#date').datetimepicker({
        lazyInit:true,
        format:'d.m.Y',
        timepicker: false,
        dayOfWeekStart:1
      });
    });
  </script>
</head>
<body>
<table width="100%" style="height: 100%">
  <tr>
    <td style="height: 100%" align="center">
      <div class="container">
        <table>
          <tr>
            <td>
              <label for="title">
                <span>
                  <fmt:message key="project.edit.title"/>
                </span>
              </label>
            </td>
            <td>
              <input type="text" id="title" autocomplete="off">
            </td>
          </tr>
          <tr>
            <td>
              <label for="date">
                <span>
                  <fmt:message key="project.edit.date"/>
                </span>
              </label>
            </td>
            <td>
              <input type="text" readonly id="date" autocomplete="off">
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <label for="description">
                <span>
                  <fmt:message key="project.edit.description"/>
                </span>
              </label>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <textarea id="description" style="width: 100%; resize: none"></textarea>
            </td>
          </tr>
        </table>
      </div>
      <div class="container">
        <input type="hidden" id="id">
        <button>
          <fmt:message key="buttons.cancel"/>
        </button>
        <button onclick="saveProject()">
          <fmt:message key="buttons.save"/>
        </button>
      </div>
    </td>
  </tr>
</table>
</body>
</html>
