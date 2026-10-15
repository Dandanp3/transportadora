<%@ page contentType="text/html; charset=UTF-8" %>
<dialog id="modal-entrega" class="modal" aria-label="entrega-titulo" data-url-busca="${pageContext.request.contextPath}/clientes/buscar">
    <form id="form-entrega" class="modal-form" action="${pageContext.request.contextPath}/entregas" method="post">
        <!-- cabecalho-->

        <header class="modal-cabecalho">
            <div>
                <h2 id="entrega-titulo">Nova entrega</h2>
                <p>Escolha os clientes e informe os detalhes da entrega</p>
            </div>
        </header>
    </form>

</dialog>

<script src="${pageContext.request.contextPath}/assets/js/entregaModal.js"></script>