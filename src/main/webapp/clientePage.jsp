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
                    <form class="busca" action="modal/clienteModal.jsp" method="get" role="search">
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
                                    <tr>
                                        <td>
                                            <span class="cliente-nome">Daniel Peixoto</span>
                                            <span class="cliente-id">ID: CL-001</span>
                                        </td>
                                        <td>***.456.789-**</td>
                                        <td><span class="tipo tipo-fisica">Pessoa física</span></td>
                                        <td class="col-acoes">
                                            <button type="button" class="btn-icone" aria-label="Ações do cliente Marcos Oliveira">
                                                <i class="ph ph-dots-three"></i>
                                            </button>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>
                                            <span class="cliente-nome">SixSeven Solutions Ltda.</span>
                                            <span class="cliente-id">ID: CL-002</span>
                                        </td>
                                        <td>**.345.678/0001-**</td>
                                        <td><span class="tipo tipo-juridica">Pessoa jurídica</span></td>
                                        <td class="col-acoes">
                                            <button type="button" class="btn-icone" aria-label="Ações do cliente Tech Solutions Ltda.">
                                                <i class="ph ph-dots-three"></i>
                                            </button>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>
                                            <span class="cliente-nome">DneyDev</span>
                                            <span class="cliente-id">ID: CL-003</span>
                                        </td>
                                        <td>***.789.123-**</td>
                                        <td><span class="tipo tipo-fisica">Pessoa física</span></td>
                                        <td class="col-acoes">
                                            <button type="button" class="btn-icone" aria-label="Ações do cliente Ana Souza">
                                                <i class="ph ph-dots-three"></i>
                                            </button>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>
                                            <span class="cliente-nome">Mercado Livre</span>
                                            <span class="cliente-id">ID: CL-004</span>
                                        </td>
                                        <td>**.567.890/0001-**</td>
                                        <td><span class="tipo tipo-juridica">Pessoa jurídica</span></td>
                                        <td class="col-acoes">
                                            <button type="button" class="btn-icone" aria-label="Ações do cliente Comercial Horizonte">
                                                <i class="ph ph-dots-three"></i>
                                            </button>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>
                                            <span class="cliente-nome">Netanyahu</span>
                                            <span class="cliente-id">ID: CL-005</span>
                                        </td>
                                        <td>***.321.654-**</td>
                                        <td><span class="tipo tipo-fisica">Pessoa física</span></td>
                                        <td class="col-acoes">
                                            <button type="button" class="btn-icone" aria-label="Ações do cliente Lucas Ferreira">
                                                <i class="ph ph-dots-three"></i>
                                            </button>
                                        </td>
                                    </tr>
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
</html>
