<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty erros ? 'Erro: ' : ''}Editar Cliente | Dandan Transportes</title>
    <script src="https://unpkg.com/@phosphor-icons/web@2.1.1"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cadastro.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/a11y.css">
    <style>
        .alerta-erro { display: flex; gap: 12px; background-color: #fef2f2; border: 1px solid #f87171; color: #991b1b; padding: 16px; border-radius: 12px; margin-bottom: 24px; }
        .alerta-erro i { font-size: 24px; color: #dc2626; }
        .alerta-erro ul { margin: 6px 0 0 20px; padding: 0; }

        .endereco-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 16px; margin-top: 16px; }
        .endereco-card { background: #f8fafc; border: 1px solid #cbd5e1; padding: 16px; border-radius: 8px; display: flex; flex-direction: column; gap: 8px; }
        .endereco-card h3 { font-size: 16px; margin: 0; color: #1e293b; }
        .endereco-card p { font-size: 14px; margin: 0; color: #475569; }
        .endereco-acoes { display: flex; gap: 8px; margin-top: auto; padding-top: 12px; border-top: 1px solid #e2e8f0; }

        .zona-perigo { margin-top: 40px; padding: 24px; border: 1px solid #fca5a5; background-color: #fef2f2; border-radius: 12px; }
        .zona-perigo h2 { color: #b91c1c; margin-bottom: 8px; font-size: 18px; }
        .zona-perigo p { color: #7f1d1d; font-size: 14px; margin-bottom: 16px; }
    </style>
</head>
<body>
    <a class="skip-link" href="#conteudo">Ir para o conteúdo principal</a>

    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main" id="conteudo" tabindex="-1">
            <div class="cadastro-container">
                <header class="cadastro-topo">
                    <a href="${pageContext.request.contextPath}/clientes" class="btn-voltar" aria-label="Voltar para a lista de clientes">
                        <i class="ph ph-arrow-left" aria-hidden="true"></i>
                    </a>
                    <div class="cadastro-titulo">
                        <h1>Editar Cliente</h1>
                        <p>Atualize os dados pessoais ou gerencie os endereços vinculados. Campos sem "(opcional)" são obrigatórios.</p>
                    </div>
                </header>

                <!-- Avisos de status -->
                <c:choose>
                    <c:when test="${param.status == 'end_atualizado'}">
                        <div class="aviso aviso-ok" role="status"><i class="ph ph-check-circle" aria-hidden="true"></i> Endereço atualizado!</div>
                    </c:when>
                    <c:when test="${param.status == 'end_excluido'}">
                        <div class="aviso aviso-ok" role="status"><i class="ph ph-trash" aria-hidden="true"></i> Endereço excluído.</div>
                    </c:when>
                </c:choose>

                <c:if test="${not empty erros}">
                    <div class="alerta-erro" role="alert" tabindex="-1" autofocus>
                        <i class="ph ph-warning-circle" aria-hidden="true"></i>
                        <div>
                            <strong>Corrija os itens abaixo para continuar:</strong>
                            <ul>
                                <c:forEach var="erro" items="${erros}">
                                    <li><c:out value="${erro}"/></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </c:if>

                <!-- dados pessoais -->
                <form action="${pageContext.request.contextPath}/clientes" method="post">
                    <input type="hidden" name="acaoForm" value="atualizarCliente">
                    <input type="hidden" name="id" value="<c:out value='${cliente.id}'/>">

                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-user" aria-hidden="true"></i> Dados Pessoais</h2>
                        <div class="form-grid">
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="documento">CPF / CNPJ</label>
                                <input type="text" id="documento" name="documento" value="<c:out value='${cliente.documento}'/>"
                                       inputmode="numeric" autocomplete="off" minlength="11" maxlength="18" required>
                            </div>
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="telefone">Telefone</label>
                                <input type="tel" id="telefone" name="telefone" value="<c:out value='${cliente.telefone}'/>"
                                       inputmode="tel" autocomplete="tel" minlength="10" maxlength="15" required>
                            </div>
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="nome">Nome completo ou Razão Social</label>
                                <input type="text" id="nome" name="nome" value="<c:out value='${cliente.nome}'/>"
                                       autocomplete="name" minlength="3" maxlength="100" required>
                            </div>
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="nomeFantasia">Nome Fantasia <span class="opcional">(opcional)</span></label>
                                <input type="text" id="nomeFantasia" name="nomeFantasia" value="<c:out value='${cliente.nomeFantasia}'/>"
                                       autocomplete="organization" maxlength="100">
                            </div>
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="email">E-mail</label>
                                <input type="email" id="email" name="email" value="<c:out value='${cliente.email}'/>"
                                       autocomplete="email" maxlength="100" required>
                            </div>
                        </div>
                        <div style="margin-top: 16px; text-align: right;">
                            <button type="submit" class="btn-principal">
                                <i class="ph ph-floppy-disk" aria-hidden="true"></i> Atualizar dados
                            </button>
                        </div>
                    </section>
                </form>

                <!-- endereços -->
                <section class="cadastro-secao" style="margin-top: 32px;" aria-labelledby="titulo-enderecos">
                    <div style="display: flex; justify-content: space-between; align-items: center; gap: 12px; flex-wrap: wrap;">
                        <h2 id="titulo-enderecos" class="secao-titulo" style="margin: 0;"><i class="ph ph-map-pin" aria-hidden="true"></i> Endereços</h2>
                        <a href="${pageContext.request.contextPath}/enderecos?acao=novo&clienteId=${cliente.id}"
                           class="btn-secundario" style="padding: 8px 16px;">
                            <i class="ph ph-plus" aria-hidden="true"></i> Adicionar endereço
                        </a>
                    </div>

                    <div class="endereco-grid">
                        <c:choose>
                            <c:when test="${empty enderecos}">
                                <p style="color: #475569; grid-column: 1 / -1;">Nenhum endereço cadastrado para esse cliente.</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="end" items="${enderecos}">
                                    <article class="endereco-card" aria-label="Endereço <c:out value='${end.logradouro}'/>">
                                        <h3><c:out value="${end.logradouro}"/>, <c:out value="${end.numero == 0 ? 'S/N' : end.numero}"/></h3>
                                        <p><c:out value="${end.bairro}"/> - <c:out value="${end.cidade}"/>/<c:out value="${end.uf}"/></p>
                                        <p>CEP: <c:out value="${end.cep}"/><c:if test="${not empty end.complemento}"> | Comp.: <c:out value="${end.complemento}"/></c:if></p>

                                        <div class="endereco-acoes">
                                            <a href="${pageContext.request.contextPath}/clientes?acao=editarEndereco&id=${end.id}&clienteId=${cliente.id}"
                                               class="btn-secundario" style="flex: 1; padding: 8px; min-height: 44px;"
                                               aria-label="Editar endereço <c:out value='${end.logradouro}'/>, <c:out value='${end.numero == 0 ? "S/N" : end.numero}'/>">
                                                <i class="ph ph-pencil-simple" aria-hidden="true"></i> Editar
                                            </a>

                                            <form action="${pageContext.request.contextPath}/clientes" method="post" style="flex: 1; display: flex;">
                                                <input type="hidden" name="acaoForm" value="excluirEndereco">
                                                <input type="hidden" name="enderecoId" value="${end.id}">
                                                <input type="hidden" name="clienteId" value="${cliente.id}">
                                                <button type="submit" class="btn-secundario btn-perigo" style="width: 100%; padding: 8px; min-height: 44px;"
                                                        aria-label="Excluir endereço <c:out value='${end.logradouro}'/>, <c:out value='${end.numero == 0 ? "S/N" : end.numero}'/>"
                                                        onclick="return confirm('Apagar este endereço? Esta ação não pode ser desfeita.')">
                                                    <i class="ph ph-trash" aria-hidden="true"></i> Excluir
                                                </button>
                                            </form>
                                        </div>
                                    </article>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </section>

                <!-- excluir cliente inteiro -->
                <section class="zona-perigo" aria-labelledby="titulo-perigo">
                    <h2 id="titulo-perigo">Zona de perigo</h2>
                    <p>Ao excluir este cliente, todos os endereços vinculados serão apagados permanentemente. Clientes que já têm entregas ou produtos vinculados não podem ser excluídos. Essa ação não pode ser desfeita.</p>
                    <form action="${pageContext.request.contextPath}/clientes" method="post">
                        <input type="hidden" name="acaoForm" value="excluirCliente">
                        <input type="hidden" name="id" value="<c:out value='${cliente.id}'/>">
                        <button type="submit" class="btn-secundario btn-perigo" style="background: #fff; border-color: #b91c1c !important;"
                                onclick="return confirm('Excluir este cliente e todos os endereços dele? Esta ação não pode ser desfeita.')">
                            <i class="ph ph-warning" aria-hidden="true"></i> Excluir cliente permanentemente
                        </button>
                    </form>
                </section>

            </div>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/mascaras.js"></script>
</body>
</html>
