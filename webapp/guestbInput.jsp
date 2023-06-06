<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>GuestB-Input</title>
    <style>
      .wrapper {
    
      }
      body {
        display: flex;
        flex-direction: column;
        align-items: center;
      }
      form{
          display: flex;
        flex-direction: column;
        border: 1px solid black;
        width: 50%;
        align-items: center;
        height: auto;
      }
      table {
      	margin-top:15px;
        algin-items:center;
        border-collapse: collapse;
        width: 50%;
        color: rgb(31, 229, 213);
      }
      th,
      td {
        border: 1px solid black;
        padding: 8px;
        text-align: center;
      }
      input {
        width: 100%;
        height: 20px;
        margin: 0px;
        padding: 0%;
        border: none;
      }
      .inputArea {
        margin-top: 15px;
        display: flex;
        background-color: rgb(209, 209, 209);
        border: 1 solid black;
        width: 50%;
        height: 300px;
        align-items: center;
      }
      .text {
        margin: 15px;
        width: 100%;
        height: 90%;
        border-radius: 8px;
        border: none;
      }
      .btn {
        margin-top: 20px;
        margin-bottom: 20px;
      }
      button {
        background-color: blue;
        border: none;
        border-radius: 5px 5px 5px 5px;
        width: 90px;
        height: 45px;
        font-size: 16px;
        color: white;
      }
    </style>
  </head>
  <body>
    <h2>방명록 입력</h2>
    <form method="post" action="/jwbook/guest.nhn?action=addList">
    
      <table>
        <tr>
          <th style="background-color: rgb(209, 209, 209)">작성자</th>
          <th>
            <input type="text" name="username" />
          </th>
        </tr>
        <tr>
          <td>이메일</td>
          <td>
            <input type="text" name="email" />
          </td>
        </tr>
        <tr>
          <td>제 목</td>
          <td>
            <input type="text" name="title" />
          </td>
        </tr>
        <tr>
          <td>비밀번호</td>
          <td>
            <input type="text" name="password" />
          </td>
        </tr>
      </table>


      <div class="inputArea">
        <input
        	type="textarea"
        	name="content"
        	class="text"
        ></input>
        </div> 
        <div class="btn">
            <button type="submit" id="submit">입력</button>
            <button type="button" id="cancle">취소</button>
            <!-- <button type="button" onclick="loacation.href = '/jwbook/src/main/java/project/GuestbController?action=listGuestb'" >목록</button> --> 
            <a href="./guestbMain.jsp"><button>목록</button></a>
        </div>
    </form>
  </body>
</html>