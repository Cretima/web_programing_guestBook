<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <title>GuestbMain</title>
    <style>
      .wrapper {
        display: flex;
        flex-direction: column;
        border: 1px solid black;
        width: 50%;
        align-items: center;
        height: 900px;
      }
      form {
        width: 90%;
        border: 2px solid rgb(55, 210, 176);
      }
      a {
        text-decoration: none;
        color: inherit;
      }

      body {
        display: flex;
        flex-direction: column;
        align-items: center;
      }
      table {
        border-collapse: collapse;
        width: 100%;
        color: black;
      }
      th {
        background-color: rgb(185, 185, 185);
      }
      td {
        background-color: rgb(80, 212, 140);
      }
      th,
      td {
        border: 1px solid rgb(141, 138, 138);
        padding: 8px;
        text-align: center;
      }
      button {
        margin-top: 25px;
        background-color: blue;
        border: none;
        border-radius: 5px 5px 5px 5px;
        width: 90px;
        height: 35px;
        font-size: 16px;
        color: white;
      }
    </style>
  </head>
  <body>
    <div class="wrapper">
      <h2>방명록 목록</h2>
      <form>
        <table>
          <tr>
            <th>번호</th>
            <th>작성자</th>
            <th>이메일</th>
            <th>작성일</th>
            <th>제목</th>
          </tr>

          <c:forEach items="${guestbList}" var="g">
            <tr class="table-success">
              <td>${g.id}</td>
              <td>${g.username}</td>
              <td>${g.email}</td>
              <td>${g.date}</td>
              <td>
                <a href="/jwbook/guest.nhn?action=getList&id=${g.id}">${g.title}</a>
              </td>
            </tr>
          </c:forEach>
        </table>
      </form>
      <a href="project/guestbInput.jsp"><button>등록</button></a>
    </div>
  </body>
</html>