<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Entregas | Dandan Transportes</title>

    <script src="https://unpkg.com/@phosphor-icons/web"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/entrega.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/entregaModal.css">

</head>
<body>
    <div class="layout-principal">
        <jsp:include page="sidebar.jsp"/>

        <main class="main">
            <header class="topo">
                <h1>Entregas</h1>
                <p>Gerencie as entregas já cadastradas.</p>

                <button type="button" class="btn-principal" id="btn-nova-entrega">
                    <i class="ph ph-plus"></i>
                    Nova entrega
                </button>
            </header>

            <div class="conteudo-principal">
                <!-- CARDS -->
                 <section class="resumo" aria-label="Resumo de Entregas">
                    <div class="resumo-card">
                        <span class="resumo-titulo">Entregas ativas</span>
                        <strong class="resumo-valor">67</strong>
                    </div>

                    <div class="resumo-card">
                        <span class="resumo-titulo">Em rota de entrega</span>
                        <strong class="resumo-valor">19</strong>
                    </div>

                    <div class="resumo-card">
                        <span class="resumo-titulo">Entregas pendentes</span>
                        <strong class="resumo-valor">48</strong>
                    </div>
                 </section>

                 <section class="painel">

                    <!---->>
                 </section>
            </div>

        </main>
    </div>
</body>