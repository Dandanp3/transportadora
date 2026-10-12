<%@ page contentType="text/html; charset=UTF-8" %>
<!-- Importando os ícones modernos -->
<script src="https://unpkg.com/@phosphor-icons/web"></script>

<style>

    .sidebar {
        width: 260px;
        background-color: #ffffff;
        border-right: 1px solid #eef2f5;
        display: flex;
        flex-direction: column;
        padding: 24px 16px;
        height: 100vh; /* ocupa 100% altura da tela*/
        position: fixed; /* fica fixo enquanto o conteudo rola*/
    }

    .brand {
        display: flex;
        align-items: center;
        gap: 12px;
        margin-bottom: 40px;
        padding: 0 8px;
    }

</style>

<aside class="sidebar">
    <div class="brand">
        <div class="brand-icon"></div>
        <div class="brand-text">
            <h2>GW</h2>
            <span>Transportes</span>
        </div>
    </div>

    <div class="menu-title"> MENU PRINCIPAL</div>
    <nav class="nav-links">
        <a href="inicio.jsp" class="menu-item">Início</a>
        <a href="entregas.jsp" class="menu-item">Entregas</a>
        <a href="clientes.jsp" class="menu-item">Clientes</a>
        <a href="simulador.jsp" class="manu-item"> Simulador de Frete</a>
    </nav>
</aside>