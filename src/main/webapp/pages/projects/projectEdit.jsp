<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ua"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="${context}/js/projects/projectEdit.js"></script>
<link rel="stylesheet" href="${context}/css/projects/ProjectEdit.css">
<table>
  <tr>
    <td style="height: 100%" align="center">
      <div style="display: flex; flex-wrap: wrap; width: fit-content">
        <div class="container">
          <table>
            <tr>
              <td>
                <label for="title">
                  <span>
                    <fmt:message key="project.edit.title"/>*
                  </span>
                </label>
              </td>
              <td>
                <input type="text" id="title" autocomplete="off">
              </td>
            </tr>
            <tr>
              <td>
                <label for="date-begin">
                  <span>
                    <fmt:message key="project.edit.date.begin"/>
                  </span>
                </label>
              </td>
              <td>
                <input id="date-begin" type="text" readonly autocomplete="off" onchange="getProjectLength()">
              </td>
            </tr>
            <tr>
              <td>
                <label for="date-complete">
                  <span>
                    <fmt:message key="project.edit.date-complete"/>
                  </span>
                </label>
              </td>
              <td>
                <input type="text" readonly id="date-complete" autocomplete="off" onchange="getProjectLength()">
              </td>
            </tr>
            <tr>
              <td colspan="2" align="right" class="project-description-label">
                <fmt:message key="project.length"/>:<span id="project-length" ></span>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <div style="display:flex; flex-wrap: wrap">
                  <tag onclick="addMonths(1)">
                    +<fmt:message key="project.edit.date.month"/>
                  </tag>
                  <tag onclick="addMonths(3)">
                    +<fmt:message key="project.edit.date.3month"/>
                  </tag>
                  <tag onclick="addMonths(6)">
                    +<fmt:message key="project.edit.date.6month"/>
                  </tag>
                  <tag onclick="addMonths(12)">
                    +<fmt:message key="project.edit.date.1year"/>
                  </tag>
                </div>
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
          <table>
            <tr>
              <td colspan="2" align="center">
                <fmt:message key="project.budget"/>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <input id="budget-none" checked type="radio" name="budget-type">
                <label for="budget-none">
                  <span>
                    <fmt:message key="project.budget.none"/>
                  </span>
                </label>
                <input id="budget-fixed" type="radio" name="budget-type">
                <label for="budget-fixed">
                  <span>
                    <fmt:message key="project.budget.fixed"/>
                  </span>
                </label>
                <input id="budget-float" type="radio" name="budget-type">
                <label for="budget-float">
                  <span>
                    <fmt:message key="project.budget.float"/>
                  </span>
                </label>

              </td>
            </tr>
            <tr>
              <td>
                <label for="budget-sum">
                  <span>
                    <fmt:message key="project.budget.sum"/>
                  </span>
                </label>
              </td>
              <td>
                <input id="budget-sum" class="budget-sum" type="number" step="100" min="0" value="0">
                <input id="budget-currency" class="budget-currency" list="currency">
                <datalist id="currency">
                  <option>UAH</option>
                  <option>USD</option>
                </datalist>
              </td>
            </tr>
          </table>
        </div>
      </div>
      <div class="container">
        <input type="hidden" id="id">
        <button onclick="closeModal()">
          <fmt:message key="buttons.cancel"/>
        </button>
        <button>
          <fmt:message key="buttons.save"/>
        </button>
      </div>
    </td>
  </tr>
</table>
</html>
