<%@ page contentType="text/html; charset=UTF-8" %>
<dialog id="modal-entrega" class="modal" aria-label="entrega-titulo" data-url-busca="${pageContext.request.contextPath}/clientes/buscar">
    <form id="form-entrega" class="modal-form" action="${pageContext.request.contextPath}/entregas" method="post">
        <!-- cabecalho-->

        <header class="modal-cabecalho">
            <div>
                <h2 id="entrega-titulo">Nova entrega</h2>
                <p>Escolha os clientes e informe os detalhes da entrega</p>
            </div>

            <button type="button" class="btn-icone" data-fechar aria-label="fechar">
                <i class="ph ph-x"></i>
            </button>
        </header>

        <!-- etapas -->
         <ol class="passos" aria-label="Etapas do cadastro">
            <li class="passo ativo">
                <span class="passo-num">1</span>
                <span class="passo-nome">Remetente e destinatário</span>
            </li>

            <li class="passo">
                <span class="passo-num">2</span>
                <span class="passo-nome">Detalhes</span>
            </li>
         </ol>

         <div class="modal-corpo">
        <!-- clientes e endereço -->

        <fieldset class="etapa">
            <legend class="sr-only">Remetente e destinatário</legend>

            <div class="form-grid">

                <!-- remetente -->
                <div class="campo span-6">
                    <h3 class="grupo-titulo">
                        <i class="ph ph-paper-plane-tilt"></i>
                    </h3>
                    <div class="campo"></div>
                </div>
            </div>
        </fieldset>
         </div>
    </form>
</dialog>

<script src="${pageContext.request.contextPath}/assets/js/entregaModal.js"></script>