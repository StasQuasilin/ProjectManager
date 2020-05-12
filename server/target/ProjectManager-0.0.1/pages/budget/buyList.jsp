<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script src="${context}/vue/transactions/buyList.vue"></script>
    <table id="buyList" style="width: 100%; height: 100%; border: solid 1pt">
        <tr>
            <td style="text-align: center">
                <span>
                    Buy List
                </span>
                <span v-on:click="newList = true">
                    +
                </span>
            </td>
        </tr>
        <tr>
            <td style="height: 100%" valign="top">
                <table v-if="newList">
                    <tr>
                        <td>
                            <input>
                        </td>
                        <td>
                            <button v-on:click="saveList">
                                Save
                            </button>
                            <span v-on:click="newList = false">
                                &times;
                            </span>
                        </td>
                    </tr>
                </table>
                <div>

                </div>
            </td>
        </tr>
    </table>
</html>