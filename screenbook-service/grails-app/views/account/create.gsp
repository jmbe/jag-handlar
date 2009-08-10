

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create Account</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Account List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Account</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${accountInstance}">
            <div class="errors">
                <g:renderErrors bean="${accountInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="username">Username:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'username','errors')}">
                                    <input type="text" id="username" name="username" value="${fieldValue(bean:accountInstance,field:'username')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userRealName">User Real Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'userRealName','errors')}">
                                    <input type="text" id="userRealName" name="userRealName" value="${fieldValue(bean:accountInstance,field:'userRealName')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="passwd">Passwd:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'passwd','errors')}">
                                    <input type="text" id="passwd" name="passwd" value="${fieldValue(bean:accountInstance,field:'passwd')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="enabled">Enabled:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'enabled','errors')}">
                                    <g:checkBox name="enabled" value="${accountInstance?.enabled}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Description:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'description','errors')}">
                                    <input type="text" id="description" name="description" value="${fieldValue(bean:accountInstance,field:'description')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email">Email:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'email','errors')}">
                                    <input type="text" id="email" name="email" value="${fieldValue(bean:accountInstance,field:'email')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="emailShow">Email Show:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'emailShow','errors')}">
                                    <g:checkBox name="emailShow" value="${accountInstance?.emailShow}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pass">Pass:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'pass','errors')}">
                                    <input type="text" name="pass" id="pass" value="${fieldValue(bean:accountInstance,field:'pass')}" />
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
