<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clientes | Dandan Transportes</title>

    <script src="https://unpkg.com/@phosphor-icons/web"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/listas.css">
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

                <button type="button" class="btn-principal" onclick="window.location.href='${pageContext.request.contextPath}/clientes?acao=novo'">
                    <i class="ph ph-plus"></i>
                    Novo cliente
                </button>
                <button type="button" class="btn-principal" onclick="window.location.href='${pageContext.request.contextPath}/clientes?acao=novo'">
                    <i class="ph ph-plus"></i>
                    Novo Endereço
                </button>
            </header>

            <div class="conteudo-principal">

                <!-- cards -->
                <section class="resumo" aria-label="Resumo de clientes">
                    <div class="resumo-card">
                        <span class="resumo-titulo">Total de clientes</span>
                        <strong class="resumo-valor">${totalClientes}</strong>
                    </div>

                    <div class="resumo-card">
                        <span class="resumo-titulo">Pessoas físicas</span>
                        <strong class="resumo-valor">${totalFisica}</strong>
                    </div>

                    <div class="resumo-card">
                        <span class="resumo-titulo">Pessoas jurídicas</span>
                        <strong class="resumo-valor">${totalJuridica}</strong>
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
                    </form>

                    <!-- lista -->
                    <div class="lista">
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
                                                <c:out value="${cliente.documentoFormatado}"/>
                                            </td>

                                            <td>
                                                <span class="tipo tipo-fisica">
                                                    <c:out value="${cliente.tipoFormatado}"/>
                                                </span>
                                            </td>

                                            <td class="col-acoes">
                                                <a href="${pageContext.request.contextPath}/clientes?acao=editar&id=${cliente.id}" 
                                                class="btn-icone" aria-label="Editar cliente" title="Editar">
                                                    <i class="ph ph-pencil-simple"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        
                    </div>

                </section>
            </div>
        </main>
    </div>

</body>
</html>
