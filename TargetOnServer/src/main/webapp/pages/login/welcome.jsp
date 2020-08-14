<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<div class="welcome-text">
    <div>
        Duis aute irure dolor in reprehenderit in voluptate, nam libero tempore,
        cum soluta nobis est eligendi optio, cumque nihil impedit, quo minus id,
        quod maxime placeat, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.

        Itaque earum rerum hic tenetur a sapiente delectus,
        quia voluptas sit, aspernatur aut odit aut fugit, sed quia consequuntur magni
        dolores eos, qui ratione voluptatem sequi nesciunt, neque porro quisquam est,
        obcaecati cupiditate non provident, similique sunt in culpa, qui officia deserunt
        mollitia animi, id est laborum et dolorum fuga? At vero eos et accusamus et iusto
        odio dignissimos ducimus, quia voluptas sit, aspernatur aut odit aut fugit, sed quia
        consequuntur magni dolores eos, qui ratione voluptatem sequi nesciunt, neque porro
        quisquam est, quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt, explicabo.

        At vero eos et accusamus et iusto odio dignissimos ducimus, qui in ea voluptate
        velit esse, quam nihil molestiae consequatur, vel illum, facere possimus, omnis
        voluptas assumenda est, omnis dolor repellendus!

        Et harum quidem rerum facilis est et expedita distinctio, qui blanditiis
        praesentium voluptatum deleniti atque corrupti, quos dolores et quas molestias
        excepturi sint, qui dolorem ipsum, quia dolor sit, amet, consectetur, adipisci velit, sed quia non...
    </div>
    <div style="margin-top: 18px">
        <fmt:message key="welcome.text"/>
    </div>
</div>