<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clientes | Dandan Transportes</title>

    <script src="https://unpkg.com/@phosphor-icons/web@2.1.1"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/listas.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/a11y.css">
</head>
<body>
    <a class="skip-link" href="#conteudo">Ir para o conteúdo principal</a>

    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main" id="conteudo" tabindex="-1">

            <header class="topo">
                <div class="topo-titulo">
                    <h1>Clientes</h1>
                    <p>Gerencie os clientes cadastrados na transportadora.</p>
                </div>

                <a href="${pageContext.request.contextPath}/clientes?acao=novo" class="btn-principal">
                    <i class="ph ph-plus" aria-hidden="true"></i>
                    Novo cliente
                </a>
            </header>

            <div class="conteudo-principal">

                <!-- aviso de status: -->
                

                <c:choose>
                    <c:when test="${param.status == 'sucesso'}">
                        <div class="aviso aviso-ok" role="status"><i class="ph ph-check-circle" aria-hidden="true"></i> Cliente cadastrado com sucesso!</div>
                    </c:when>
                    <c:when test="${param.status == 'atualizado'}">
                        <div class="aviso aviso-ok" role="status"><i class="ph ph-check-circle" aria-hidden="true"></i> Dados do cliente atualizados!</div>
                    </c:when>
                    <c:when test="${param.status == 'excluido'}">
                        <div class="aviso aviso-ok" role="status"><i class="ph ph-trash" aria-hidden="true"></i> Cliente excluído do sistema.</div>
                    </c:when>
                    <c:when test="${param.status == 'erro_exclusao'}">
                        <div class="aviso aviso-erro" role="alert"><i class="ph ph-warning" aria-hidden="true"></i>Não foi possível excluir: este cliente possui entregas vinculadas.</div>
                    </c:when>
                </c:choose>

                <!-- Cards Resumo -->
                <section class="resumo" aria-label="Resumo de clientes">
                    <div class="resumo-card">
                        <span class="resumo-titulo">Total de clientes</span>
                        <strong class="resumo-valor">${totalClientes != null ? totalClientes : 0}</strong>
                    </div>

                    <div class="resumo-card">
                        <span class="resumo-titulo">Pessoas físicas</span>
                        <strong class="resumo-valor">${totalFisica != null ? totalFisica : 0}</strong>
                    </div>

                    <div class="resumo-card">
                        <span class="resumo-titulo">Pessoas jurídicas</span>
                        <strong class="resumo-valor">${totalJuridica != null ? totalJuridica : 0}</strong>
                    </div>
                </section>

                <section class="painel" aria-labelledby="titulo-lista-clientes">

                    <!-- busca -->
                    <form class="busca" action="${pageContext.request.contextPath}/clientes" method="get" role="search">
                        <div class="busca-campo">
                            <i class="ph ph-magnifying-glass"></i>
                            <input type="search"
                                name="q"
                                placeholder="Buscar por CPF / CNPJ"
                                aria-label="Buscar Clientes"
                                autocomplete="off"
                                data-busca-tabela="tabela-clientes"
                                data-busca-colunas="1"
                                data-busca-modo="numerico"
                                data-busca-vazio="Nenhum cliente encontrado para essa busca.">
                        </div>
                    </form>

                    <h2 id="titulo-lista-clientes" class="sr-only">Lista de clientes</h2>

                    <div class="lista">
                        <div class="tabela-wrapper" role="region" aria-label="Tabela de clientes (role a tabela horizontalmente se necessário)" tabindex="0">
                            <table class="tabela-dados" id="tabela-clientes">
                                <caption class="sr-only">Clientes cadastrados</caption>
                                <thead>
                                    <tr>
                                        <th scope="col">Cliente</th>
                                        <th scope="col">CPF / CNPJ</th>
                                        <th scope="col">Tipo</th>
                                        <th scope="col" class="col-acoes"><span class="sr-only">Ações</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${empty clientes}">
                                            <tr>
                                                <td colspan="4" style="text-align: center; padding: 32px; color: #475569;">
                                                    Nenhum cliente cadastrado ainda. Clique em "Novo cliente" para começar.
                                                </td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="cliente" items="${clientes}">
                                                <tr>
                                                    <td>
                                                        <span class="dado-principal">
                                                            <c:out value="${cliente.nome}"/>
                                                        </span>
                                                        <span class="dado-secundario">
                                                            ID: <c:out value="${cliente.id}"/>
                                                        </span>
                                                    </td>

                                                    <td>
                                                        <c:out value="${cliente.documento}"/>
                                                    </td>

                                                    <td>
                                                        <span class="tipo ${cliente.tipoPessoa == 'F' ? 'tipo-fisica' : 'tipo-juridica'}">
                                                            <c:out value="${cliente.tipoPessoa == 'F' ? 'Física' : 'Jurídica'}"/>
                                                        </span>
                                                    </td>

                                                    <td class="col-acoes">
                                                        <a href="${pageContext.request.contextPath}/clientes?acao=editar&id=${cliente.id}"
                                                           class="btn-icone" aria-label="Editar cliente <c:out value='${cliente.nome}'/>">
                                                            <i class="ph ph-pencil-simple" aria-hidden="true"></i>
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                    <tr id="sem-resultado" style="display: none;">
                                        <td colspan="4" style="text-align: center; padding: 32px; color: #475569;">
                                            Nenhum cliente encontrado para essa busca.
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>

            </div>
        </main>
    </div>
</body>
<script src="./assets/js/busca.js"></script>
</html>
