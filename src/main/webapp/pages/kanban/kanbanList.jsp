<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 27.02.2020
  Time: 15:17
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>

<style>
    .border{
        display: inline-flex;
        height: 100%;
        overflow-y: scroll;
        border: solid black 1pt;
        width: 30%;
    }
    .border-title{
        width: 100%;
        background-color: #e6ffe6;
        font-weight: bold;
    }
    .border-item{
        padding: 2pt 4pt;
        border: solid #a7a766;

        margin: 2pt;
        border-radius: 3pt;
    }
    .todo-border{

    }
    .todo-border .border-item{
        background-color: #ffeece;
    }
    .progressing-border{

    }
    .progressing-border .border-item{
        background-color: #c6fb8c;
    }
    .done-border{

    }
</style>
    <script>
        var kanban = new Vue({
            el:'#kanban',
            data:{
                api:{},
                task:{},
                todo:[],
                inProgress:[],
                done:[]
            },
            methods:{
                open:function(task){
                    if (!task.edit) {
                        this.task = task;
                        this.getSubTasks(task.id);
                    }
                },
                getSubTasks:function(parent){
                    const self = this;
                    PostApi(this.api.getSub, {parent:parent, status:'active'}, function(a){
                        if (a.status === 'success'){
                            Vue.set(self, 'todo', a.result);
                        }
                    })
                },
                editTask:function(task){
                    if (!task){
                        task = {

                        };
                        this.todo.push(task);
                    }
                    Vue.set(task, 'edit', true);
                },
                saveTask:function(task){
                    var t = Object.assign({}, task);
                    t.parent = this.task.id;
                    delete t.edit;
                    if (!t.id){
                        t.id = -1;
                    }
                    PostApi(this.api.saveTask, t, function(a){
                        task.id = a.result;
                        Vue.set(task, 'edit', false);
                    })
                },
                drag:function(a){
                    console.log('set save attribute for \'' + a.title + '\'');
                    Vue.set(a, 'save', true);
                },
                drop:function(a, status){
                    const self = this;
                    var checkList = function(){
                        a.forEach(function(item){
                            if (item.save){
                                PostApi(self.api.changeStatus, {id:item.id, status:status}, function(a){
                                    console.log(a);
                                });
                                item.status = status;
                                Vue.set(item, 'save', false);
                            }
                        })
                    };
                    setTimeout(checkList, 100);
                },
                check:function(task){
                    console.log(task)
                },
                log: function(evt) {
                    console.log(evt);
                }
            }
        });
        kanban.api.saveTask = '${save}';
        kanban.api.getSub = '${getSubTasks}';
        kanban.api.changeStatus = '${changeStatus}';
        <c:forEach items="${tasks}" var="task">
        kanban.todo.push(${task.task.toJson()});
        </c:forEach>
        <c:forEach items="${progressing}" var="t">
        kanban.inProgress.push(${t.toJson()});
        </c:forEach>
    </script>
    <table id="kanban" style="width: 100%; height: 100%">
        <tr>
            <td>
                <template v-if="task.title">
                    <b>
                        {{task.title}}
                    </b>
                    <span>
                        /
                    </span>
                </template>
                <span v-if="task.path" v-for="p in task.path" v-on:click="open()">
                    <a>
                        {{p}}
                    </a>
                    <span>
                        /
                    </span>
                </span>
                <a v-on:click="open()">
                    ..
                </a>
            </td>
        </tr>
        <tr>
            <td style="width: 100%; height: 100%">
                <div class="border todo-border" >
                    <div style="width: 100%">
                        <div class="border-title">
                            <span>
                                TODO
                            </span>
                            <span v-if="task.id" style="float: right" v-on:click="editTask()">
                                +
                            </span>
                        </div>
                        <draggable :list="todo" group="task" style="border: solid grey 1pt; min-height: 480px" @add="drop(todo, 'active')" >
                                <div class="border-item" v-for="task in todo" v-on:dragend="drag(task)"
                                     :key="task.id" v-on:click.right="editTask(task)"
                                     v-on:click="open(task)">
                                    <div v-if="!task.edit">
                                        <span>
                                            {{task.title}}
                                        </span>
                                        <span style="font-size: 10pt">
                                            {{task.status}}
                                        </span>
                                    </div>
                                    <div v-else>
                                        <table>
                                            <tr>
                                                <td>
                                                    <fmt:message key="task.title"/>
                                                </td>
                                                <td>
                                                    <input v-model="task.title" v-on:keyup.enter="saveTask(task)">
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                        </draggable>
                    </div>
                </div>
                <div class="border progressing-border">
                    <div style="width: 100%">
                        <div class="border-title">
                            <span>
                                In Progress
                            </span>
                        </div>
                        <draggable :list="inProgress" group="task" @add="drop(inProgress, 'progressing')" style="border: solid grey 1pt; min-height: 480px">
                                <div class="border-item" v-for="task in inProgress" v-on:dragend="drag(task)"
                                     :key="task.id" v-on:click.right="editTask(task)">
                                    <div v-if="!task.edit">
                                        <span>
                                            {{task.title}}
                                        </span>
                                        <span style="font-size: 10pt">
                                            {{task.status}}
                                        </span>
                                    </div>
                                    <div v-else>
                                        <table>
                                            <tr>
                                                <td>
                                                    <fmt:message key="task.title"/>
                                                </td>
                                                <td>
                                                    <input v-model="task.title" v-on:keyup.enter="saveTask(task)">
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                        </draggable>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</html>
