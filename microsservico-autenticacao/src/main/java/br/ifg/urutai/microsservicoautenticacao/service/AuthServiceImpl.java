package br.ifg.urutai.microsservicoautenticacao.service;

import br.ifg.urutai.microsservicoautenticacao.grpc.LoginRequest;
import br.ifg.urutai.microsservicoautenticacao.grpc.LoginResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthServiceImpl extends br.ifg.urutai.microsservicoautenticacao.grpc.AuthServiceGrpc.AuthServiceImplBase {

    @Override
    public void validarLogin(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        String email = request.getEmail();
        String senhaDigitada = request.getSenhaDigitada();
        String senhaDoBanco = request.getSenhaDoBanco();

        System.out.println(">>> Validando credenciais gRPC para: " + email);

        LoginResponse response;

        if (senhaDigitada.equals(senhaDoBanco)) {
            response = LoginResponse.newBuilder()
                    .setSucesso(true)
                    .setToken("TOKEN_JWT_DINAMICO_OK_" + email.hashCode())
                    .setMensagem("Autenticado com sucesso!")
                    .build();
        } else {
            response = LoginResponse.newBuilder()
                    .setSucesso(false)
                    .setToken("")
                    .setMensagem("Senha incorreta.")
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}