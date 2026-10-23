<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nova Entrega | Dandan Transportes</title>
    <script src="https://unpkg.com/@phosphor-icons/web@2.1.1"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cadastro.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/a11y.css">
</head>
<body>
    <a class="skip-link" href="#conteudo">Ir para o conteúdo principal</a>

    <div class="layout-principal">
        <jsp:include page="sidebar.jsp" />

        <main class="main" id="conteudo" tabindex="-1">
            <div class="cadastro-container">
                <header class="cadastro-topo">
                    <a href="${pageContext.request.contextPath}/entregas" class="btn-voltar" aria-label="Voltar para a lista de entregas">
                        <i class="ph ph-arrow-left" aria-hidden="true"></i>
                    </a>
                    <div class="cadastro-titulo">
                        <h1>Nova Entrega</h1>
                        <p>Defina a origem, destino e a carga do catálogo. Campos marcados com * são obrigatórios.</p>
                    </div>
                </header>

                <form action="${pageContext.request.contextPath}/entregas" method="post">
                    <input type="hidden" name="acaoForm" value="salvarEntrega">

                    <!-- clientes / endereços -->
                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-users" aria-hidden="true"></i> Envolvidos no Transporte</h2>
                        <div class="form-grid">

                            <div class="campo span-3">
                                <label class="campo-rotulo" for="remetenteId">Remetente (quem envia) *</label>
                                <select id="remetenteId" name="remetenteId" required>
                                    <option value="" disabled selected>Escolha o remetente...</option>
                                    <c:forEach var="cliente" items="${clientes}">
                                        <option value="${cliente.id}"><c:out value="${cliente.nome} (${cliente.documento})"/></option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="campo span-3">
                                <label class="campo-rotulo" for="enderecoOrigemId">Endereço de coleta *</label>
                                <select id="enderecoOrigemId" name="enderecoOrigemId" required disabled aria-describedby="aviso-origem">
                                    <option value="" disabled selected>Escolha o remetente primeiro...</option>
                                    <c:forEach var="end" items="${enderecos}">
                                        <option value="${end.id}" class="opt-endereco-origem" data-cliente="${end.clienteId}">
                                            <c:out value="${end.logradouro}, ${end.numero == 0 ? 'S/N' : end.numero} - ${end.cidade}/${end.uf}"/>
                                        </option>
                                    </c:forEach>
                                </select>
                                <p id="aviso-origem" class="campo-ajuda" role="status"></p>
                            </div>

                            <div class="campo span-3">
                                <label class="campo-rotulo" for="destinatarioId">Destinatário (quem recebe) *</label>
                                <select id="destinatarioId" name="destinatarioId" required>
                                    <option value="" disabled selected>Escolha o destinatário...</option>
                                    <c:forEach var="cliente" items="${clientes}">
                                        <option value="${cliente.id}"><c:out value="${cliente.nome} (${cliente.documento})"/></option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="campo span-3">
                                <label class="campo-rotulo" for="enderecoDestinoId">Endereço de entrega *</label>
                                <select id="enderecoDestinoId" name="enderecoDestinoId" required disabled aria-describedby="aviso-destino">
                                    <option value="" disabled selected>Escolha o destinatário primeiro...</option>
                                    <c:forEach var="end" items="${enderecos}">
                                        <option value="${end.id}" class="opt-endereco-destino" data-cliente="${end.clienteId}">
                                            <c:out value="${end.logradouro}, ${end.numero == 0 ? 'S/N' : end.numero} - ${end.cidade}/${end.uf}"/>
                                        </option>
                                    </c:forEach>
                                </select>
                                <p id="aviso-destino" class="campo-ajuda" role="status"></p>
                            </div>
                        </div>
                    </section>

                    <!-- carga -->
                    <section class="cadastro-secao">
                        <h2 class="secao-titulo"><i class="ph ph-package" aria-hidden="true"></i> Dados da Carga</h2>
                        <div class="form-grid">
                            <div class="campo span-4">
                                <label class="campo-rotulo" for="produtoId">Produto (catálogo) *</label>
                                <select id="produtoId" name="produtoId" required>
                                    <option value="" disabled selected>Escolha a carga...</option>
                                    <c:forEach var="produto" items="${produtos}">
                                        <option value="${produto.id}">
                                            <c:out value="${produto.descricao}"/> (base: <fmt:formatNumber value="${produto.valorPadrao}" type="currency" currencyCode="BRL"/>)
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="campo span-2">
                                <label class="campo-rotulo" for="quantidade">Quantidade *</label>
                                <input type="number" id="quantidade" name="quantidade" value="1" min="1" max="9999"
                                       inputmode="numeric" required>
                            </div>
                        </div>
                    </section>

                    <div class="cadastro-rodape">
                        <a href="${pageContext.request.contextPath}/entregas" class="btn-secundario">Cancelar</a>
                        <button type="submit" class="btn-principal" style="cursor: pointer;">
                            <i class="ph ph-truck" aria-hidden="true"></i> Gerar Entrega e Frete
                        </button>
                    </div>
                </form>
            </div>
        </main>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            var selectOrigem = document.getElementById('enderecoOrigemId');
            var selectDestino = document.getElementById('enderecoDestinoId');
            var selectRemetente = document.getElementById('remetenteId');
            var selectDestinatario = document.getElementById('destinatarioId');

            // guarda as options originais de cliente (sem o placeholder)
            var remetentesData = Array.from(selectRemetente.querySelectorAll('option:not([value=""])'));
            var destinatariosData = Array.from(selectDestinatario.querySelectorAll('option:not([value=""])'));

            // guarda as options originais de endereço
            var origensData = Array.from(selectOrigem.querySelectorAll('.opt-endereco-origem'));
            var destinosData = Array.from(selectDestino.querySelectorAll('.opt-endereco-destino'));

            // esvazia os selects até escolher o cliente
            selectOrigem.innerHTML = '<option value="" disabled selected>Escolha o remetente primeiro...</option>';
            selectDestino.innerHTML = '<option value="" disabled selected>Escolha o destinatário primeiro...</option>';

            function filtrarEnderecos(clienteSelectId, enderecoSelect, dadosOriginais, avisoId) {
                var clienteId = document.getElementById(clienteSelectId).value;
                var aviso = document.getElementById(avisoId);
                enderecoSelect.innerHTML = '<option value="" disabled selected>Escolha o endereço...</option>';
                enderecoSelect.disabled = false;
                aviso.textContent = '';

                var encontrou = false;
                dadosOriginais.forEach(function (opt) {
                    if (opt.getAttribute('data-cliente') === clienteId) {
                        enderecoSelect.appendChild(opt.cloneNode(true));
                        encontrou = true;
                    }
                });

                if (!encontrou) {
                    aviso.textContent = 'Este cliente não possui endereços cadastrados.';
                }
            }

            // elect de clientes sem o cliente que ja foi escolhido 
            function refazerClientes(select, dadosOriginais, textoPlaceholder, idExcluido) {
                var selecionado = select.value;
                select.innerHTML = '<option value="" disabled>' + textoPlaceholder + '</option>';

                dadosOriginais.forEach(function (opt) {
                    if (opt.value !== idExcluido) {
                        select.appendChild(opt.cloneNode(true));
                    }
                });

                // mantém a escolha anterior se ela ainda existe; senão volta pro placeholder
                if (selecionado && selecionado !== idExcluido) {
                    select.value = selecionado;
                } else {
                    select.selectedIndex = 0;
                }
            }

            selectRemetente.addEventListener('change', function () {
                filtrarEnderecos('remetenteId', selectOrigem, origensData, 'aviso-origem');
                // o remetente escolhido some da lista de destinatários
                refazerClientes(selectDestinatario, destinatariosData, 'Escolha o destinatário...', selectRemetente.value);
            });
            selectDestinatario.addEventListener('change', function () {
                filtrarEnderecos('destinatarioId', selectDestino, destinosData, 'aviso-destino');
                // o destinatário escolhido some da lista de remetentes
                refazerClientes(selectRemetente, remetentesData, 'Escolha o remetente...', selectDestinatario.value);
            });
        });
    </script>
</body>
</html>
