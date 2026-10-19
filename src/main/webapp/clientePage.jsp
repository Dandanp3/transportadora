<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clientes | Dandan Transportes</title>

    <script src="https://unpkg.com/@phosphor-icons/web"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/clientes.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/clienteModal.css">

</head>
<body>
    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main">

            <header class="topo">
                <div class="topo-titulo">
                    <h1>Clientes</h1>
                    <p>Gerencie os clientes cadastrados na transportadora.</p>
                </div>

                <button type="button" class="btn-principal" id="btn-novo-cliente">
                    <i class="ph ph-plus"></i>
                    Novo cliente
                </button>
            </header>

            <div class="conteudo-principal">

                <!-- cards -->
                <section class="resumo" aria-label="Resumo de clientes">
                    <div class="resumo-card">
                        <span class="resumo-titulo">Total de clientes</span>
                        <strong class="resumo-valor">1.284</strong>
                    </div>

                    <div class="resumo-card">
                        <span class="resumo-titulo">Pessoas físicas</span>
                        <strong class="resumo-valor">856</strong>
                    </div>

                    <div class="resumo-card">
                        <span class="resumo-titulo">Pessoas jurídicas</span>
                        <strong class="resumo-valor">428</strong>
                    </div>
                </section>

                <section class="painel">

                    <!-- busca -->
                    <form class="busca" action="clienteModal.jsp" method="get" role="search">
                        <div class="busca-campo">
                            <i class="ph ph-magnifying-glass"></i>
                            <input type="search"
                                   name="q"
                                   placeholder="Buscar por nome, CPF/CNPJ ou código..."
                                   aria-label="Buscar clientes">
                        </div>

                        <button type="button" class="btn-icone" aria-label="Filtros avançados">
                            <i class="ph ph-sliders-horizontal"></i>
                        </button>
                    </form>

                    <!-- links com ?tipo=, a aba ativa recebe a classe ativo -->
                    <nav class="filtros" aria-label="Filtrar por tipo">
                        <a href="?tipo=todos" class="filtro ativo">Todos</a>
                        <a href="?tipo=fisica" class="filtro">Pessoa física</a>
                        <a href="?tipo=juridica" class="filtro">Pessoa jurídica</a>
                    </nav>

                    <!-- lista -->
                    <div class="lista">
                        <div class="lista-cabecalho">
                            <h2>Lista de clientes</h2>
                            <span class="lista-contagem">1–5 de 1.284</span>
                        </div>

                        <div class="tabela-wrapper">
                            <p>Clientes recebidos: ${clientes.size()}</p>
                            <table class="tabela-clientes">
                                <thead>
                                    <tr>
                                        <th>Cliente</th>
                                        <th>CPF / CNPJ</th>
                                        <th>Tipo</th>
                                        <th class="col-acoes"><span class="sr-only">Ações</span></th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <c:forEach var="cliente" items="${clientes}">
                                        <p>Objeto: ${cliente}</p>
                                            <p>ID: ${cliente.id}</p>
                                            <p>Nome: ${cliente.nome}</p>
                                            <p>Documento: ${cliente.documento}</p>
                                            <hr>
                                    </c:forEach>


                                    <c:forEach var="cliente" items="${clientes}">

                                        


                                        <tr>
                                            <td>
                                                <span class="cliente-nome">
                                                    <c:out value="${cliente.nome}"/>
                                                </span>
                                                <span class="cliente-id">
                                                    <c:out value="${cliente.id}"/>
                                                </span>
                                            </td>

                                            <td>
                                                <c:out value="${cliente.documento}"/>
                                            </td>

                                            <td>
                                                <span class="tipo tipo-fisica">Pessoa física</span>
                                            </td>

                                            <td class="col-acoes">
                                                <button type="button" class="btn-icone"
                                                        aria-label="Ações do cliente">
                                                    <i class="ph ph-dots-three"></i>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <footer class="lista-rodape">
                            <span class="lista-info">5 clientes por página</span>

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

    <!-- modal futuro de cadastro -->
    <jsp:include page="clienteModal.jsp" /> 

</body>

<p>Teste EL: ${1 + 1}</p>
<p>Nome: ${clientes[0].nome}</p>
<p>Documento: ${clientes[0].documento}</p>
</html>
