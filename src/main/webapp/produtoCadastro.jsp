<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty produto ? 'Editar' : 'Novo'} Produto | Dandan Transportes</title>
    <script src="https://unpkg.com/@phosphor-icons/web"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cadastro.css">
</head>
<body>
    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main">
            <div class="cadastro-container">
                <header class="cadastro-topo">
                    <a href="${pageContext.request.contextPath}/produtos" class="btn-voltar" aria-label="Voltar">
                        <i class="ph ph-arrow-left"></i>
                    </a>
                    <div class="cadastro-titulo">
                        <h1>${not empty produto ? 'Editar' : 'Novo'} Produto</h1>
                        <p>Preencha os dados do catálogo de carga.</p>
                    </div>
                </header>
                
                <!-- alertas de Erro -->
                <c:if test="${not empty erros}">
                    <div class="alerta-erro" style="background: #fef2f2; border: 1px solid #f87171; color: #991b1b; padding: 16px; border-radius: 8px; margin-bottom: 20px;">
                        <strong>Verifique os seguintes erros:</strong>
                        <ul style="margin-top: 8px; margin-left: 20px;">
                            <c:forEach var="erro" items="${erros}">
                                <li>${erro}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/produtos" method="post">
                    <!-- guarda o id se for update -->
                    <input type="hidden" name="id" value="${produto.id}">
                    
                    <!-- Se tiver produto, a acao padrao do form vira atualizar -->
                    <c:if test="${not empty produto}">
                        <input type="hidden" name="acaoForm" value="atualizarProduto">
                    </c:if>

                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-package"></i> Dados do Produto</h2>
                        <div class="form-grid">
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="descricao">Descrição do Produto (Nome ou Categoria)</label>
                                <input type="text" id="descricao" name="descricao" placeholder="Ex: Caixa Genérica até 5kg" 
                                       value="${produto.descricao}"
                                       minlength="3" maxlength="150" required>
                            </div>
                            
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="valorPadrao">Valor Padrão (R$)</label>
                                <!-- type text pra mascara de virgula funcionar direito -->
                                <input type="text" id="valorPadrao" name="valorPadrao" placeholder="0,00" 
                                       value="${produto.valorPadrao}"
                                       oninput="mascaraMoeda(this)" required>
                            </div>
                            
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="pesoPadrao">Peso Padrão (Kg)</label>
                                <input type="text" id="pesoPadrao" name="pesoPadrao" placeholder="0,000"
                                       value="${produto.pesoPadrao}"
                                       oninput="mascaraPeso(this)" required>
                            </div>
                        </div>
                    </section>

                    <div class="cadastro-rodape">
                        <!-- se for update, mostra o botao de excluir isolado -->
                        <c:if test="${not empty produto}">
                            <button type="submit" name="acaoForm" value="excluirProduto" class="btn-secundario" 
                                    style="color: #ef4444; border-color: #fca5a5; margin-right: auto; cursor: pointer;" 
                                    onclick="return confirm('ATENÇÃO: Tem certeza que deseja excluir este produto do catálogo permanentemente?');">
                                <i class="ph ph-trash"></i> Excluir Produto
                            </button>
                        </c:if>

                        <a href="${pageContext.request.contextPath}/produtos" class="btn-secundario">Cancelar</a>
                        
                        <button type="submit" class="btn-principal">
                            <i class="${not empty produto ? 'ph ph-floppy-disk' : 'ph ph-check'}"></i> 
                            ${not empty produto ? 'Atualizar Produto' : 'Salvar Produto'}
                        </button>
                    </div>
                </form>
            </div>
        </main>
    </div>

    <!-- mascaras -->
    <script src="${pageContext.request.contextPath}/assets/js/mascaras.js"></script>
</body>
</html>