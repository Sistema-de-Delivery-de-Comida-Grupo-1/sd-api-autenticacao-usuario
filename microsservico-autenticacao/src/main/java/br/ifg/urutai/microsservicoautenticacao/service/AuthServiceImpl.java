//package br.ifg.urutai.microsservicoautenticacao.service;
//
//import net.devh.boot.grpc.server.service.GrpcService;
//
//@GrpcService
//public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {
//
//    @Override
//    public void validarLogin(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
//        String email = request.getEmail();
//        String senha = request.getSenha();
//
//        System.out.println(">>> Requisição gRPC recebida para o e-mail: " + email);
//
//        LoginResponse response;
//
//        // Validação acadêmica simples em texto plano
//        if (senha.equals("senhaSegura123")) {
//            response = LoginResponse.newBuilder()
//                    .setSucesso(true)
//                    .setToken("TOKEN_JWT_SIMULADO_VALIDEZ_OK_123")
//                    .setMensagem("Autenticado com sucesso")
//                    .build();
//        } else {
//            response = LoginResponse.newBuilder()
//                    .setSucesso(false)
//                    .setToken("")
//                    .setMensagem("Credenciais inválidas")
//                    .build();
//        }
//
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//}
