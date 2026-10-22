<%@ page contentType="text/html; charset=UTF-8" %>

<script src="https://unpkg.com/@phosphor-icons/web"></script>
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

<style>
    :root {
        --sidebar-collapsed: 72px;
        --sidebar-expanded: 250px;
        --primary: #0d6efd;
        --text: #555353;
        --hover-bg: #eef3ff;
    }

    * {
        margin: 0;
        padding: 0;
        border: none;
        outline: none;
        text-decoration: none;
        list-style: none;
        box-sizing: border-box;
        font-family: "Poppins", sans-serif;
    }

    nav {
        border-radius: 12px;
    }

    .sidebar {
        position: sticky;
        top: 0;
        left: 0;
        height: 100vh;
        width: var(--sidebar-collapsed);
        flex-shrink: 0;
        background-color: #fff;
        overflow: hidden;
        white-space: nowrap;
        transition: width 0.3s ease;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.08);
        z-index: 1000;
    }

    .sidebar:hover {
        width: var(--sidebar-expanded);
    }

    /* LOGO */
    .sidebar .logo {
        display: flex;
        align-items: center;
        height: 80px;
        padding: 0 14px;
        border-bottom: 1px solid #f0f0f0;
        margin-bottom: 10px;
    }

    .sidebar .logo img {
        width: 44px;
        height: 44px;
        min-width: 44px;
        object-fit: contain;
    }

    .sidebar .logo .brand {
        margin-left: 12px;
        font-size: 14px;
        font-weight: 600;
        line-height: 1.2;
        color: #333;
        text-transform: uppercase;
        opacity: 0;
        transition: opacity 0.2s ease;
    }

    .sidebar:hover .logo .brand {
        opacity: 1;
        transition-delay: 0.1s;
    }

    /*  MENU  */
    .sidebar ul {
        padding: 0 10px;
    }

    .sidebar ul li a {
        display: flex;
        align-items: center;
        height: 48px;
        padding: 0 14px;
        margin-bottom: 4px;
        border-radius: 10px;
        color: var(--text);
        font-size: 14px;
        font-weight: 500;
        transition: background 0.2s, color 0.2s;
    }

    .sidebar ul li a i {
        font-size: 22px;
        min-width: 24px;
        text-align: center;
    }

    .sidebar ul li a .nav-item {
        margin-left: 16px;
        opacity: 0;
        transition: opacity 0.2s ease;
    }

    .sidebar:hover ul li a .nav-item {
        opacity: 1;
        transition-delay: 0.1s;
    }

    .sidebar ul li a:hover {
        background: var(--hover-bg);
        color: var(--primary);
    }
</style>

<nav class="sidebar">
    <div class="logo">
        <img src="./assets/truck.png" alt="Logo Dandan Transportes">
        <span class="brand">Dandan<br>Transportes</span>
    </div>

    <ul>
        <li>
            <a href="./index.jsp">
                <i class="ph ph-house"></i>
                <span class="nav-item">Início</span>
            </a>
        </li>

        <li>
            <a href="./clientes">
                <i class="ph ph-users"></i>
                <span class="nav-item">Clientes</span>
            </a>
        </li>

        <li>
            <a href="./enderecos">
                <i class="ph ph-users"></i>
                <span class="nav-item">Enderecos</span>
            </a>
        </li>

        <li>
            <a href="./entregas">
                <i class="ph ph-package"></i>
                <span class="nav-item">Entregas</span>
            </a>
        </li>

        <li>
            <a href="./produtos">
                <i class="ph ph-calculator"></i>
                <span class="nav-item">Produtos</span>
            </a>
        </li>
        
    </ul>
</nav>