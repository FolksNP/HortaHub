//
//  AssinanteController.swift
//  HortaHub
//

import Foundation

func requireVerificationCode(
    numero: String,
    completion: @escaping (Result<String, Error>) -> Void
) {

    let endpoint = "http://localhost:8080/verificacao/enviar/\(numero)"

    guard let url = URL(string: endpoint) else {
        completion(.failure(URLError(.badURL)))
        return
    }

    URLSession.shared.dataTask(with: url) { data, response, error in

        if let error = error {
            completion(.failure(error))
            return
        }

        guard
            let data = data,
            let response = response as? HTTPURLResponse,
            response.statusCode == 200
        else {
            completion(.failure(URLError(.badServerResponse)))
            return
        }

        do {

            let decoder = JSONDecoder()
            decoder.keyDecodingStrategy = .convertFromSnakeCase

            let resultado = try decoder.decode(String.self, from: data)

            completion(.success(resultado))

        } catch {
            completion(.failure(error))
        }

    }.resume()
}

func verifyVerificationCode(
    numero: String,
    codigo: String,
    completion: @escaping (Result<Bool, Error>) -> Void
) {

    let endpoint = "http://localhost:8080/verificacao/verificar/\(numero)/\(codigo)"

    guard let url = URL(string: endpoint) else {
        completion(.failure(URLError(.badURL)))
        return
    }

    URLSession.shared.dataTask(with: url) { data, response, error in

        if let error = error {
            completion(.failure(error))
            return
        }

        guard
            let data = data,
            let response = response as? HTTPURLResponse,
            response.statusCode == 200
        else {
            completion(.failure(URLError(.badServerResponse)))
            return
        }

        do {

            let decoder = JSONDecoder()
            decoder.keyDecodingStrategy = .convertFromSnakeCase

            let resultado = try decoder.decode(Bool.self, from: data)

            completion(.success(resultado))

        } catch {
            completion(.failure(error))
        }

    }.resume()
}
