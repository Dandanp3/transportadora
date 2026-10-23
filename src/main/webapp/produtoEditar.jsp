<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Produto | Dandan Transportes</title>
    <script src="https://unpkg.com/@phosphor-icons/web"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cadastro.css">
    <style>
        .alerta-erro { display: flex; gap: 12px; background-color: #fef2f2; border: 1px solid #f87171; color: #991b1b; padding: 16px; border-radius: 12px; margin-bottom: 24px; }
        .alerta-erro i { font-size: 24px; color: #ef4444; }
        
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
                    <a href="${pageContext.request.contextPath}/produtos" class="btn-voltar" aria-label="Voltar">
                        <i class="ph ph-arrow-left"></i>
                    </a>
                    <div class="cadastro-titulo">
                        <h1>Editar Produto</h1>
                        <p>Atualize os dados deste item no catálogo.</p>
                    </div>
                </header>

                <c:if test="${not empty erros}">
                    <div class="alerta-erro">
                        <i class="ph ph-warning-circle"></i>
                        <div>
                            <strong>Erro, favor ajustar:</strong>
                            <ul style="margin-top: 4px; margin-left: 20px;">
                                <c:forEach var="erro" items="${erros}"><li>${erro}</li></c:forEach>
                            </ul>
                        </div>
                    </div>
                </c:if>

                <!-- form 1 - atualizar dados -->
                <form action="${pageContext.request.contextPath}/produtos" method="post">
                    <input type="hidden" name="acaoForm" value="atualizarProduto">
                    <input type="hidden" name="id" value="${produto.id}">

                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-package"></i> Dados do Produto</h2>
                        <div class="form-grid">
                            <div class="campo span-6">
                                <label class="campo-rotulo" for="descricao">Descrição do Produto (Nome ou Categoria)</label>
                                <input type="text" id="descricao" name="descricao" value="${produto.descricao}" minlength="3" maxlength="150" required>
                            </div>
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="valorPadrao">Valor Padrão (R$)</label>
                                <input type="text" id="valorPadrao" name="valorPadrao" value="${produto.valorPadrao}" oninput="mascaraMoeda(this)" required>
                            </div>
                            <div class="campo span-3">
                                <label class="campo-rotulo" for="pesoPadrao">Peso Padrão (Kg)</label>
                                <input type="text" id="pesoPadrao" name="pesoPadrao" value="${produto.pesoPadrao}" oninput="mascaraPeso(this)" required>
                            </div>
                        </div>
                        <div style="margin-top: 16px; text-align: right;">
                            <button type="submit" class="btn-principal"><i class="ph ph-floppy-disk"></i> Atualizar Dados</button>
                        </div>
                    </section>
                </form>

                <!-- form 2 - excluir produto inteiro -->
                <section class="zona-perigo">
                    <h2>ZONA DE PERIGO</h2>
                    <p>Excluindo este produto, a ação não terá volta.</p>
                    <form action="${pageContext.request.contextPath}/produtos" method="post">
                        <input type="hidden" name="acaoForm" value="excluirProduto">
                        <input type="hidden" name="id" value="${produto.id}">
                        <button type="submit" class="btn-secundario" style="cursor: pointer; color: #ef4444; border-color: #ef4444; background: white;" onclick="return confirm('Certeza absoluta? Vai apagar este produto do catálogo.');">
                            <i class="ph ph-warning"></i> Excluir Produto Permanentemente
                        </button>
                    </form>
                </section>

            </div>
        </main>
    </div>

    <!-- Masscaras inline -->
    <script>
        function mascaraMoeda(input) {
            let valor = input.value;
            valor = valor.replace(/\D/g, "");
            valor = valor.replace(/(\d)(\d{2})$/, "$1,$2");
            valor = valor.replace(/(?=(\d{3})+(\D))\B/g, ".");
            input.value = valor;
        }

        function mascaraPeso(input) {
            let valor = input.value;
            valor = valor.replace(/[^\d,]/g, "");
            input.value = valor;
        }
        
        window.onload = function() {
            let valorInput = document.getElementById("valorPadrao");
            let pesoInput = document.getElementById("pesoPadrao");
            
            if(valorInput.value && !valorInput.value.includes(',')) {
                valorInput.value = parseFloat(valorInput.value).toFixed(2).replace('.', ',');
            }
            if(pesoInput.value && !pesoInput.value.includes(',')) {
                pesoInput.value = parseFloat(pesoInput.value).toFixed(3).replace('.', ',');
            }
        };
    </script>
</body>
</html>