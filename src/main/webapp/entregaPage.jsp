<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/modal.css">

</head>
<body>

    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main">
            <header class="topo">
                <div class="topo-titulo">
                    <h1>Entregas</h1>
                    <p>Gerencie as entregas já cadastradas.</p>
                </div>

                <button type="button" class="btn-principal" id="btn-nova-entrega">
                    <i class="ph ph-plus"></i>
                    Nova entrega
                </button>
            </header>

            <div class="conteudo-principal">

                <!-- Cards -->
                <section class="resumo" aria-label="Resumo de entregas">
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

                <!-- painel principal -->
                <section class="painel">

                    <form class="busca" method="get" role="search">
                        <input type="hidden" name="status" value="${param.status}">

                        <div class="busca-campo">
                            <i class="ph ph-magnifying-glass"></i>
                            <input type="search"
                                   name="q"
                                   value="${param.q}"
                                   placeholder="Buscar por código, cidade, estado, CEP ou CPF do destinatário"
                                   aria-label="Buscar entregas">
                        </div>

                        <button type="button" class="btn-icone" aria-label="Filtros avançados">
                            <i class="ph ph-sliders-horizontal"></i>
                        </button>
                    </form>

                    <nav class="filtros" aria-label="Filtrar por status">
                        <a href="?status=todas"     class="filtro ativo">Todas</a>
                        <a href="?status=pendente"  class="filtro">Pendentes</a>
                        <a href="?status=transito"  class="filtro">Em trânsito</a>
                        <a href="?status=rota"      class="filtro">Em rota</a>
                        <a href="?status=concluida" class="filtro">Concluídas</a>
                    </nav>

                    <!-- Lista -->
                    <div class="lista">
                        <div class="lista-cabecalho">
                            <h2>Lista de entregas</h2>
                            <span class="lista-contagem">1–5 de 200</span>
                        </div>

                        <div class="tabela-wrapper">
                            <table class="tabela-entregas">
                                <caption class="sr-only">Lista de entregas cadastradas</caption>
                                <thead>
                                    <tr>
                                        <th scope="col">Entrega</th>
                                        <th scope="col">Remetente</th>
                                        <th scope="col">Destinatário</th>
                                        <th scope="col">Destino</th>
                                        <th scope="col">Status</th>
                                        <th scope="col" class="col-acoes"><span class="sr-only">Ações</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <!--<c:forEach var="entrega" items="${entregas}"></c:forEach> -->
                                        <tr>
                                            <td>
                                                <span class="texto-principal"></span>
                                                <span class="texto-secundario">Criada em 13/10/2026</span>
                                            </td>
                                            <td>
                                                <span class="texto-principal">SixtySeven Solutions LTDA.</span>
                                                <span class="texto-secundario">ID: CL-009</span>
                                            </td>
                                            <td>
                                                <span class="texto-principal">DneyDev</span>
                                                <span class="texto-secundario">***.456.789-**</span>
                                            </td>
                                            <td>
                                                <span class="texto-principal">Belo Horizonte – MG</span>
                                                <span class="texto-secundario">CEP 30140-071</span>
                                            </td>
                                            <td><span class="status status-rota">Em rota</span></td>
                                            <td class="col-acoes">
                                                <button type="button" class="btn-icone" aria-label="Ações da entrega BR03594754">
                                                    <i class="ph ph-dots-three"></i>
                                                </button>
                                            </td>
                                        </tr>

                                        
                                   
                                </tbody>
                            </table>

                        </div>

                        <footer class="lista-rodape">
                            <span class="lista-info">5 entregas por página</span>

                            <nav class="paginacao" aria-label="Paginação">
                                <a href="?pagina=0" class="pag-btn" aria-label="Página anterior">
                                    <i class="ph ph-caret-left"></i>
                                </a>
                                <a href="?pagina=1" class="pag-btn ativo" aria-current="page">1</a>
                                <a href="?pagina=2" class="pag-btn">2</a>
                                <a href="?pagina=3" class="pag-btn">3</a>
                                <a href="?pagina=2" class="pag-btn" aria-label="Próxima página">
                                    <i class="ph ph-caret-right"></i>
                                </a>
                            </nav>
                        </footer>
                    </div>

                </section>
            </div>
        </main>
    </div>

    <jsp:include page="entregaModal.jsp"/>

</body>
</html>
