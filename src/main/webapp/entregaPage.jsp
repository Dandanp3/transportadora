<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Entregas | Dandan Transportes</title>

    <script src="https://unpkg.com/@phosphor-icons/web@2.1.1"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/entrega.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/modal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/a11y.css">

    <style>
        .status-select { border: none; border-radius: 9999px; padding: 4px 12px; font-size: 0.875rem; font-weight: 500; cursor: pointer; }
        .status-select.pendente { background-color: #fef08a; color: #854d0e; }
        .status-select.rota { background-color: #bae6fd; color: #075985; }
        .status-cancelado { background-color: #fecaca; color: #991b1b; padding: 4px 12px; border-radius: 9999px; font-size: 0.875rem; font-weight: 500; display: inline-block; }
    </style>
</head>
<body>
    <a class="skip-link" href="#conteudo">Ir para o conteúdo principal</a>

    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main" id="conteudo" tabindex="-1">
            <header class="topo">
                <div class="topo-titulo">
                    <h1>Entregas</h1>
                    <p>Gerencie as entregas já cadastradas.</p>
                </div>

                <a href="${pageContext.request.contextPath}/entregas?acao=novo" class="btn-principal">
                    <i class="ph ph-plus" aria-hidden="true"></i>
                    Nova entrega
                </a>
            </header>

            <div class="conteudo-principal">

                <!-- status -->
                <c:choose>
                    <c:when test="${param.status == 'sucesso'}">
                        <div class="aviso aviso-ok" role="status"><i class="ph ph-check-circle" aria-hidden="true"></i> Entrega gerada com sucesso!</div>
                    </c:when>
                    <c:when test="${param.status == 'status_atualizado'}">
                        <div class="aviso aviso-ok" role="status"><i class="ph ph-check-circle" aria-hidden="true"></i> Status da entrega atualizado.</div>
                    </c:when>
                    <c:when test="${param.status == 'excluido'}">
                        <div class="aviso aviso-ok" role="status"><i class="ph ph-trash" aria-hidden="true"></i> Entrega excluída do sistema.</div>
                    </c:when>
                    <c:when test="${param.status == 'erro_produto'}">
                        <div class="aviso aviso-erro" role="alert"><i class="ph ph-warning" aria-hidden="true"></i> O produto selecionado não foi encontrado. Escolha outro e tente novamente.</div>
                    </c:when>
                    <c:when test="${param.status == 'erro_mesmo_cliente'}">
                        <div class="aviso aviso-erro" role="alert"><i class="ph ph-warning" aria-hidden="true"></i> O remetente e o destinatário não podem ser o mesmo cliente.</div>
                    </c:when>
                    <c:when test="${param.status == 'erro_banco' || param.status == 'erro_excecao'}">
                        <div class="aviso aviso-erro" role="alert"><i class="ph ph-warning" aria-hidden="true"></i> Erro interno ao processar a entrega.</div>
                    </c:when>
                </c:choose>
                <!--cards -->
                <c:set var="totalRota" value="${0}" />
                <c:set var="totalPendentes" value="${0}" />
                <c:forEach var="e" items="${not empty todasEntregas ? todasEntregas : entregas}">
                    <c:set var="s" value="${e.statusEntrega.toUpperCase()}" />
                    <c:if test="${s == 'PENDENTE'}">
                        <c:set var="totalPendentes" value="${totalPendentes + 1}" />
                    </c:if>
                    <c:if test="${s == 'EM ROTA' || s == 'EM_ROTA'}">
                        <c:set var="totalRota" value="${totalRota + 1}" />
                    </c:if>
                </c:forEach>

                <section class="resumo" aria-label="Resumo de entregas">
                    <div class="resumo-card">
                        <span class="resumo-titulo">Entregas ativas</span>
                        <strong class="resumo-valor">${totalPendentes + totalRota}</strong>
                    </div>

                    <div class="resumo-card">
                        <span class="resumo-titulo">Em rota de entrega</span>
                        <strong class="resumo-valor">${totalRota}</strong>
                    </div>

                    <div class="resumo-card">
                        <span class="resumo-titulo">Entregas pendentes</span>
                        <strong class="resumo-valor">${totalPendentes}</strong>
                    </div>
                </section>

                <section class="painel" aria-labelledby="titulo-lista-entregas">
                    <h2 id="titulo-lista-entregas" class="sr-only">Lista de entregas</h2>

                    <!-- busca -->
                    <form class="busca" action="${pageContext.request.contextPath}/produtos" method="get" role="search">
                        <div class="busca-campo">
                            <i class="ph ph-magnifying-glass"></i>
                            <input type="search"
                                   name="q"
                                   placeholder="Buscar pela NF-e"
                                   aria-label="Buscar entregas"
                                   data-busca-tabela="tabela-entregas"
                                   data-busca-colunas="0"
                                   data-busca-modo="texto"
                                   data-busca-vazio="Nenhuma ehntrega encontrada para essa busca.">
                        </div>
                    </form>

                    <c:if test="${not empty param.q}">
                        <p role="status" class="campo-ajuda" style="margin-bottom: 12px;">
                            ${entregas.size()} resultado(s) para "<c:out value='${param.q}'/>".
                        </p>
                    </c:if>

                    <div class="lista">
                        <div class="tabela-wrapper" role="region" aria-label="Tabela de entregas (role a tabela horizontalmente se necessário)" tabindex="0">
                            <table class="tabela-entregas" id="tabela-entregas">
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
                                    <c:choose>
                                        <c:when test="${empty entregas}">
                                            <tr>
                                                <td colspan="6" style="text-align: center; padding: 32px; color: #475569;">
                                                    <c:choose>
                                                        <c:when test="${not empty param.q}">Nenhuma entrega encontrada para essa busca.</c:when>
                                                        <c:otherwise>Nenhuma entrega registrada. Clique em "Nova entrega" para começar.</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="entrega" items="${entregas}">
                                                <c:set var="st" value="${entrega.statusEntrega.toUpperCase()}" />

                                                <tr>
                                                    <td>
                                                        <button type="button" class="btn-expandir" aria-expanded="false"
                                                                aria-controls="detalhes-${entrega.id}"
                                                                onclick="toggleDetalhes(this, '${entrega.id}')">
                                                            <i class="ph ph-caret-right" aria-hidden="true"></i>
                                                            <span class="btn-expandir-texto">
                                                                <span class="texto-principal">NFe: <c:out value="${entrega.numeroNotaFiscal}"/></span>
                                                                <span class="texto-secundario">Emissão: <c:out value="${entrega.dataEmissao}"/></span>
                                                                <span class="sr-only">Mostrar ou ocultar itens da carga</span>
                                                            </span>
                                                        </button>
                                                    </td>
                                                    <td>
                                                        <span class="texto-principal"><c:out value="${entrega.remetente.nome}"/></span>
                                                        <span class="texto-secundario">Doc: <c:out value="${entrega.remetente.documento}"/></span>
                                                    </td>
                                                    <td>
                                                        <span class="texto-principal"><c:out value="${entrega.destinatario.nome}"/></span>
                                                        <span class="texto-secundario">Doc: <c:out value="${entrega.destinatario.documento}"/></span>
                                                    </td>
                                                    <td>
                                                        <span class="texto-principal"><c:out value="${entrega.enderecoDestino.cidade}"/> – <c:out value="${entrega.enderecoDestino.uf}"/></span>
                                                        <span class="texto-secundario">CEP <c:out value="${entrega.enderecoDestino.cep}"/></span>
                                                    </td>

                                                    <!-- status -->
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${st == 'ENTREGUE' || st == 'CANCELADO'}">
                                                                <span class="${st == 'ENTREGUE' ? 'status status-concluida' : 'status-cancelado'}">
                                                                    <c:out value="${entrega.statusEntrega}"/>
                                                                </span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <form action="${pageContext.request.contextPath}/entregas" method="post" class="status-form">
                                                                    <input type="hidden" name="acaoForm" value="atualizarStatus">
                                                                    <input type="hidden" name="id" value="${entrega.id}">
                                                                    <label class="sr-only" for="status-${entrega.id}">Status da entrega NFe <c:out value="${entrega.numeroNotaFiscal}"/></label>
                                                                    <select id="status-${entrega.id}" name="novoStatus" class="status-select ${st == 'PENDENTE' ? 'pendente' : 'rota'}">
                                                                        <c:choose>
                                                                            <c:when test="${st == 'PENDENTE'}">
                                                                                <option value="PENDENTE" selected>Pendente</option>
                                                                                <option value="EM ROTA">Em rota</option>
                                                                                <option value="CANCELADO">Cancelado</option>
                                                                            </c:when>
                                                                        
                                                                            <c:otherwise>
                                                                                <option value="EM ROTA">EM rota</option>
                                                                                <option value="ENTREGUE">Entregue</option>
                                                                                <option value="CANCELADO">Cancelado</option>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </select>
                                                                    <button type="submit" class="btn-secundario btn-compacto" onclick="return confirm('Salvar o novo status? Entregue e Cancelado não podem ser alterados depois.')">Salvar status</button>
                                                                </form>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>

                                                    <td class="col-acoes">
                                                        <form action="${pageContext.request.contextPath}/entregas" method="post" style="margin: 0;">
                                                            <input type="hidden" name="acaoForm" value="excluirEntrega">
                                                            <input type="hidden" name="id" value="${entrega.id}">
                                                            <button type="submit" class="btn-icone btn-perigo" style="border: 0; background: none;"
                                                                    aria-label="Excluir entrega NFe <c:out value='${entrega.numeroNotaFiscal}'/>"
                                                                    onclick="return confirm('Apagar esta entrega? Os itens dela também serão apagados.')">
                                                                <i class="ph ph-trash" aria-hidden="true"></i>
                                                            </button>
                                                        </form>
                                                    </td>
                                                </tr>

                                                <tr id="detalhes-${entrega.id}" hidden style="background-color: #f8fafc;">
                                                    <td colspan="6" style="padding: 16px; border-bottom: 2px solid #e2e8f0; border-top: 1px dashed #cbd5e1;">
                                                        <div style="display: flex; justify-content: space-between; align-items: center; gap: 16px; flex-wrap: wrap;">
                                                            <div>
                                                                <strong style="color: #0f172a; display: block; margin-bottom: 8px;">Itens da carga:</strong>
                                                                <ul style="list-style-type: disc; margin-left: 20px; color: #334155;">
                                                                    <c:forEach var="item" items="${entrega.itens}">
                                                                        <li>
                                                                            <c:out value="${item.quantidade}"/>x <strong><c:out value="${item.produto.descricao}"/></strong>
                                                                            (<fmt:formatNumber value="${item.valorUnitario}" type="currency" currencyCode="BRL"/> base)
                                                                        </li>
                                                                    </c:forEach>
                                                                </ul>
                                                            </div>
                                                            <div style="text-align: right;">
                                                                <strong style="color: #0f172a;">Frete total calculado:</strong><br>
                                                                <span style="font-size: 18px; color: #15803d; font-weight: bold;">
                                                                    <fmt:formatNumber value="${entrega.valorFrete}" type="currency" currencyCode="BRL"/>
                                                                </span>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
        </main>
    </div>

    <script>
        function toggleDetalhes(btn, id) {
            var linha = document.getElementById('detalhes-' + id);
            var abrir = linha.hidden;
            linha.hidden = !abrir;
            btn.setAttribute('aria-expanded', abrir ? 'true' : 'false');
        }
    </script>
</body>
<script src="./assets/js/busca.js"></script>
</html>
