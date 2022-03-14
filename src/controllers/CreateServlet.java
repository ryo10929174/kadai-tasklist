package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();
            em.getTransaction().begin();

            // Taskのインスタンスを生成
            Task t = new Task();

            // tの各フィールドにデータを代入
            String content = request.getParameter("content");
            t.setContent(content);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis()); // 現在の日時を取得
            t.setCreated_at(currentTime);
            t.setUpdated_at(currentTime);

            // データベースに保存
            em.persist(t);
            em.getTransaction().commit();
            em.close();

            // 自動採番されたIDの値を表示
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }

}
