<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Cliente | Dandan Transportes</title>
    <script src="https://unpkg.com/@phosphor-icons/web"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cadastro.css">
    <style>
        .alerta-erro { display: flex; gap: 12px; background-color: #fef2f2; border: 1px solid #f87171; color: #991b1b; padding: 16px; border-radius: 12px; margin-bottom: 24px; }
        .alerta-erro i { font-size: 24px; color: #ef4444; }
        
        /* css bolado pros cards de endereco */
        .endereco-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 16px; margin-top: 16px; }
        .endereco-card { background: #f8fafc; border: 1px solid #e2e8f0; padding: 16px; border-radius: 8px; display: flex; flex-direction: column; gap: 8px; }
        .endereco-card h3 { font-size: 16px; margin: 0; color: #1e293b; }
        .endereco-card p { font-size: 14px; margin: 0; color: #64748b; }
        .endereco-acoes { display: flex; gap: 8px; margin-top: auto; padding-top: 12px; border-top: 1px solid #e2e8f0; }
        
        .zona-perigo { margin-top: 40px; padding: 24px; border: 1px solid #fca5a5; background-color: #fef2f2; border-radius: 12px; }
        .zona-perigo h2 { color: #dc2626; margin-bottom: 8px; font-size: 18px; }
        .zona-perigo p { color: #991b1b; font-size: 14px; margin-bottom: 16px; }
    </style>
</head>
<body>
    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main">
            <div class="cadastro-container">
                <header class="cadastro-topo">
                    <a href="${pageContext.request.contextPath}/clientes" class="btn-voltar" aria-label="Voltar">
                        <i class="ph ph-arrow-left"></i>
                    </a>
                    <div class="cadastro-titulo">
                        <h1>Editar Cliente</h1>
                        <p>Atualize os dados pessoais ou gerencie os endereços vinculados.</p>
                    </div>
                </header>

                <c:if test="${not empty erros}">
                    <div class="alerta-erro">
                        <i class="ph ph-warning-circle"></i>
                        <div>
                            <strong>Deu ruim, arruma isso aí:</strong>
                            <ul><c:forEach var="erro" items="${erros}"><li>${erro}</li></c:forEach></ul>
                        </div>
                    </div>
                </c:if>

                <!-- dados 1 -->
                <form action="${pageContext.request.contextPath}/clientes" method="post">
                    <input type="hidden" name="acaoForm" value="atualizarCliente">
                    <input type="hidden" name="id" value="${cliente.id}">

                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-user"></i> Dados Pessoais</h2>
                        <div class="form-grid">
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="documento">CPF / CNPJ</label>
                                <input type="text" id="documento" name="documento" value="${cliente.documento}" minlength="14" maxlength="18" required>
                            </div>
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="telefone">Telefone</label>
                                <input type="tel" id="telefone" name="telefone" value="${cliente.telefone}" minlength="14" maxlength="15" required>
                            </div>
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="nome">Nome completo ou Razão Social</label>
                                <input type="text" id="nome" name="nome" value="${cliente.nome}" minlength="3" maxlength="100" required>
                            </div>
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="nomeFantasia">Nome Fantasia <span class="opcional">(Opcional)</span></label>
                                <input type="text" id="nomeFantasia" name="nomeFantasia" value="${cliente.nomeFantasia}" maxlength="100">
                            </div>
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="email">E-mail</label>
                                <input type="email" id="email" name="email" value="${cliente.email}" maxlength="100" required>
                            </div>
                        </div>
                        <div style="margin-top: 16px; text-align: right;">
                            <button type="submit" class="btn-principal"><i class="ph ph-floppy-disk"></i> Atualizar Dados</button>
                        </div>
                    </section>
                </form>

                <section class="cadastro-secao" style="margin-top: 32px;">
                    <div style="display: flex; justify-content: space-between; align-items: center;">
                        <h2 class="secao-titulo" style="margin: 0;"><i class="ph ph-map-pin"></i> Endereços</h2>
                        <button type="button" class="btn-secundario" style="cursor: pointer; padding: 8px 16px;"><i class="ph ph-plus"></i> Adicionar Endereço</button>
                    </div>

                    <div class="endereco-grid">
                        <c:choose>
                            <c:when test="${empty enderecos}">
                                <p style="color: #64748b; grid-column: 1 / -1;">Nenhum endereço cadastrado pra esse cliente.</p>
                            </c:when>
                            <c:otherwise>
                                <!-- loop pra mostrar todos os enderecos do cara -->
                                <c:forEach var="end" items="${enderecos}">
                                    <div class="endereco-card">
                                        <h3>${end.logradouro}, ${end.numero}</h3>
                                        <p>${end.bairro} - ${end.cidade}/${end.uf}</p>
                                        <p>CEP: ${end.cep} <c:if test="${not empty end.complemento}"> | Comp: ${end.complemento}</c:if></p>
                                        
                                        <div class="endereco-acoes">
                                            <a href="${pageContext.request.contextPath}/clientes?acao=editarEndereco&id=${end.id}&clienteId=${cliente.id}" class="btn-secundario" style="flex: 1; padding: 6px; display: flex; align-items: center; justify-content: center; text-decoration: none;">
                                                <i class="ph ph-pencil-simple" style="margin-right: 4px;"></i> Editar
                                            </a>
                                            
                                            <!-- mini form invisivel so pra excluir esse endereco especifico -->
                                            <form action="${pageContext.request.contextPath}/clientes" method="post" style="flex: 1; display: flex;">
                                                <input type="hidden" name="acaoForm" value="excluirEndereco">
                                                <input type="hidden" name="enderecoId" value="${end.id}">
                                                <input type="hidden" name="clienteId" value="${cliente.id}">
                                                <button type="submit" class="btn-secundario" style="cursor: pointer; color: #ef4444; border-color: #fca5a5; width: 100%; padding: 6px;" onclick="return confirm('Apagar esse endereço?');">
                                                    <i class="ph ph-trash"></i> Excluir
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </section>

                <!-- form 2 - excluir cliente inteiro -->
                <section class="zona-perigo">
                    <h2>ZONA DE PERIGO</h2>
                    <p>Ao excluir este cliente, todos os endereços e históricos vinculados a ele serão apagados para sempre. Essa ação não tem volta.</p>
                    <form action="${pageContext.request.contextPath}/clientes" method="post">
                        <input type="hidden" name="acaoForm" value="excluirCliente">
                        <input type="hidden" name="id" value="${cliente.id}">
                        <button type="submit" class="btn-secundario" style="cursor: pointer; color: #ef4444; border-color: #ef4444; background: white;" onclick="return confirm('Certeza absoluta? Vai apagar tudo do cliente.');">
                            <i class="ph ph-warning"></i> Excluir Cliente Permanentemente
                        </button>
                    </form>
                </section>

            </div>
        </main>
    </div>

    <!-- mascaras -->
    <script src="${pageContext.request.contextPath}/assets/js/mascaras.js"></script>
</body>
</html>