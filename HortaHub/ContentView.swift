import SwiftUI
import Combine

// MARK: - Navigation State
class AppRouter: ObservableObject {
    @Published var currentScreen: Screen = .landing

    enum Screen {
        case landing, phoneInput, verification, plans
    }

    var phoneNumber: String = ""
}

// MARK: - Content View
struct ContentView: View {
    @StateObject private var router = AppRouter()

    var body: some View {
        switch router.currentScreen {
        case .landing:
            LandingView()
                .environmentObject(router)
        case .phoneInput:
            PhoneInputView()
                .environmentObject(router)
        case .verification:
            VerificationView()
                .environmentObject(router)
        case .plans:
            PlansView()
                .environmentObject(router)
        }
    }
}

// MARK: - Colors
extension Color {
    static let hortaDarkGreen  = Color(red: 0.08, green: 0.25, blue: 0.08)
    static let hortaMidGreen   = Color(red: 0.12, green: 0.38, blue: 0.12)
    static let hortaLightGreen = Color(red: 0.20, green: 0.52, blue: 0.18)
    static let hortaSheet      = Color(red: 0.93, green: 0.95, blue: 0.91)
    static let hortaButton     = Color(red: 0.88, green: 0.92, blue: 0.86)
}

// MARK: - Shared Header
struct HortaHeader: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            Image(systemName: "leaf.fill")
                .font(.system(size: 32))
                .foregroundColor(.green)
                .rotationEffect(.degrees(-20))

            Text("HortaHub")
                .font(.system(size: 42, weight: .bold))
                .foregroundColor(.white)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.horizontal, 28)
        .padding(.top, 60)
    }
}

// MARK: - 1. Landing Page
struct LandingView: View {
    @EnvironmentObject var router: AppRouter

    var body: some View {
        ZStack {
            // Background gradient
            LinearGradient(
                colors: [.hortaDarkGreen, .hortaMidGreen, .hortaLightGreen],
                startPoint: .top,
                endPoint: .bottom
            )
            .ignoresSafeArea()

            VStack(spacing: 0) {
                HortaHeader()

                Text("A assinatura mais\nsaudável que pode ter")
                    .font(.system(size: 18, weight: .regular))
                    .foregroundColor(.white.opacity(0.85))
                    .multilineTextAlignment(.leading)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.horizontal, 28)
                    .padding(.top, 12)

                Spacer()

                // Placeholder image box
                RoundedRectangle(cornerRadius: 12)
                    .strokeBorder(Color.white.opacity(0.4), style: StrokeStyle(lineWidth: 1.5, dash: [8]))
                    .frame(width: 160, height: 160)
                    .overlay(
                        Image(systemName: "photo")
                            .font(.system(size: 36))
                            .foregroundColor(.white.opacity(0.3))
                    )

                Spacer()

                // Buttons
                VStack(spacing: 14) {
                    Button {
                        router.currentScreen = .phoneInput
                    } label: {
                        Text("Iniciar")
                            .font(.system(size: 17, weight: .semibold))
                            .foregroundColor(.hortaDarkGreen)
                            .frame(maxWidth: .infinity)
                            .frame(height: 54)
                            .background(Color.white)
                            .clipShape(Capsule())
                    }

                    Button {
                        router.currentScreen = .plans
                    } label: {
                        Text("Visualizar planos")
                            .font(.system(size: 17, weight: .semibold))
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .frame(height: 54)
                            .background(Color.white.opacity(0.2))
                            .clipShape(Capsule())
                    }
                }
                .padding(.horizontal, 28)
                .padding(.bottom, 48)
            }
        }
    }
}

// MARK: - 2. Phone Input
struct PhoneInputView: View {
    @EnvironmentObject var router: AppRouter
    @State private var phone = ""

    var body: some View {
        ZStack(alignment: .top) {
            // Top green area
            LinearGradient(
                colors: [.hortaDarkGreen, .hortaMidGreen],
                startPoint: .top,
                endPoint: .bottom
            )
            .ignoresSafeArea()

            VStack(spacing: 0) {
                HortaHeader()
                    .padding(.bottom, 32)

                // Bottom sheet
                VStack(spacing: 20) {
                    Spacer().frame(height: 24)

                    VStack(alignment: .leading, spacing: 10) {
                        Text("Número de celular:")
                            .font(.system(size: 15, weight: .medium))
                            .foregroundColor(.gray)

                        TextField("(DDD) 90000-0000", text: $phone)
                            .keyboardType(.phonePad)
                            .font(.system(size: 16))
                            .padding(.horizontal, 16)
                            .frame(height: 50)
                            .background(Color.white)
                            .clipShape(RoundedRectangle(cornerRadius: 12))
                            .overlay(
                                RoundedRectangle(cornerRadius: 12)
                                    .strokeBorder(Color.gray.opacity(0.2), lineWidth: 1)
                            )
                    }
                    .padding(.horizontal, 24)

                    Button {
                        router.phoneNumber = phone
                        router.currentScreen = .verification
                    } label: {
                        Text("Entrar")
                            .font(.system(size: 17, weight: .semibold))
                            .foregroundColor(.hortaDarkGreen)
                            .frame(maxWidth: .infinity)
                            .frame(height: 54)
                            .background(Color.white)
                            .clipShape(Capsule())
                            .shadow(color: .black.opacity(0.08), radius: 4, y: 2)
                    }
                    .padding(.horizontal, 24)

                    Spacer()
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .background(
                    Color.hortaSheet
                        .clipShape(
                            RoundedRectangle(cornerRadius: 32, style: .continuous)
                        )
                )
            }
        }
    }
}

// MARK: - 3. Verification
struct VerificationView: View {
    @EnvironmentObject var router: AppRouter
    @State private var code = ""
    @State private var timeRemaining = 59
    @State private var timer: Timer? = nil

    // Masked phone display
    var maskedPhone: String {
        let p = router.phoneNumber.isEmpty ? "11 9****-0123" : router.phoneNumber
        guard p.count > 6 else { return p }
        let start = p.prefix(4)
        let end = p.suffix(4)
        return "\(start)****-\(end)"
    }

    var body: some View {
        ZStack(alignment: .top) {
            LinearGradient(
                colors: [.hortaDarkGreen, .hortaMidGreen],
                startPoint: .top,
                endPoint: .bottom
            )
            .ignoresSafeArea()

            VStack(spacing: 0) {
                HortaHeader()
                    .padding(.bottom, 32)

                // Bottom sheet
                VStack(spacing: 0) {
                    Spacer().frame(height: 32)

                    // Greeting
                    HStack(spacing: 6) {
                        Text("Olá, seja bem vindo!")
                            .font(.system(size: 18, weight: .bold))
                            .foregroundColor(.hortaDarkGreen)
                        Text("🙂")
                            .font(.system(size: 18))
                    }
                    .frame(maxWidth: .infinity, alignment: .center)

                    Spacer().frame(height: 16)

                    Text("Enviamos um código para você no número:")
                        .font(.system(size: 14))
                        .foregroundColor(.gray)
                        .multilineTextAlignment(.center)

                    Text(maskedPhone)
                        .font(.system(size: 14, weight: .semibold))
                        .foregroundColor(.hortaDarkGreen)
                        .padding(.top, 2)

                    Spacer().frame(height: 20)

                    Text("Digite o código abaixo para iniciar o seu cadastro")
                        .font(.system(size: 14))
                        .foregroundColor(.gray)
                        .multilineTextAlignment(.center)
                        .padding(.horizontal, 32)

                    Spacer().frame(height: 16)

                    // Code field
                    SecureField("*****", text: $code)
                        .keyboardType(.numberPad)
                        .font(.system(size: 18, weight: .medium))
                        .multilineTextAlignment(.center)
                        .padding(.horizontal, 16)
                        .frame(height: 50)
                        .background(Color.white)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                        .overlay(
                            RoundedRectangle(cornerRadius: 12)
                                .strokeBorder(Color.gray.opacity(0.2), lineWidth: 1)
                        )
                        .padding(.horizontal, 24)

                    Spacer().frame(height: 12)

                    // Timer + resend
                    HStack(spacing: 8) {
                        Text(String(format: "0:%02d", timeRemaining))
                            .font(.system(size: 13, weight: .medium))
                            .foregroundColor(.gray)

                        Button("Enviar novamente") {
                            timeRemaining = 59
                        }
                        .font(.system(size: 13))
                        .foregroundColor(.hortaDarkGreen)
                        .disabled(timeRemaining > 0)
                    }
                    .padding(.horizontal, 24)
                    .frame(maxWidth: .infinity, alignment: .leading)

                    Spacer().frame(height: 24)

                    Button {
                        router.currentScreen = .plans
                    } label: {
                        Text("Verificar")
                            .font(.system(size: 17, weight: .semibold))
                            .foregroundColor(.hortaDarkGreen)
                            .frame(maxWidth: .infinity)
                            .frame(height: 54)
                            .background(Color.white)
                            .clipShape(Capsule())
                            .shadow(color: .black.opacity(0.08), radius: 4, y: 2)
                    }
                    .padding(.horizontal, 24)

                    Spacer()

                    // Back button
                    Button {
                        router.currentScreen = .phoneInput
                    } label: {
                        HStack(spacing: 6) {
                            Image(systemName: "chevron.left")
                                .font(.system(size: 13, weight: .semibold))
                            Text("Não é seu número?")
                                .font(.system(size: 14))
                        }
                        .foregroundColor(.gray)
                    }
                    .padding(.bottom, 36)
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .background(
                    Color.hortaSheet
                        .clipShape(RoundedRectangle(cornerRadius: 32, style: .continuous))
                )
            }
        }
        .onAppear { startTimer() }
        .onDisappear { timer?.invalidate() }
    }

    private func startTimer() {
        timer?.invalidate()
        timer = Timer.scheduledTimer(withTimeInterval: 1, repeats: true) { _ in
            if timeRemaining > 0 { timeRemaining -= 1 }
            else { timer?.invalidate() }
        }
    }
}

// MARK: - Plan Model
struct Plan: Identifiable {
    let id = UUID()
    let name: String
    let price: Int
    let items: [String]
    let buttonLabel: String
}

let hortaPlans: [Plan] = [
    Plan(name: "Horti One",  price: 139, items: ["4 Frutas", "3 Legumes", "2 Verduras", "1Kg de Grãos"], buttonLabel: "Selecionar"),
    Plan(name: "Essencial",  price: 219, items: ["6 Frutas", "5 Legumes", "3 Verduras", "1Kg Grãos", "1 Especiaria"], buttonLabel: "Selecionar"),
    Plan(name: "Horti Life", price: 319, items: ["8 Frutas", "6 Legumes", "4 Verduras", "1.5KG Grãos", "2 Especiarias"], buttonLabel: "Selecionar"),
    Plan(name: "Horti Pro+", price: 439, items: ["10 Frutas", "8 Legumes", "5 Verduras", "2KG Grãos", "4 Especiarias"], buttonLabel: "Assinar condição exclusiva"),
]

// MARK: - 4. Plans (Carousel)
struct PlansView: View {
    @EnvironmentObject var router: AppRouter
    @State private var selectedIndex = 0

    var body: some View {
        ZStack(alignment: .top) {
            LinearGradient(
                colors: [.hortaDarkGreen, .hortaMidGreen],
                startPoint: .top,
                endPoint: .bottom
            )
            .ignoresSafeArea()

            VStack(spacing: 0) {
                // Header
                HStack(alignment: .center) {
                    Image(systemName: "leaf.fill")
                        .font(.system(size: 24))
                        .foregroundColor(.green)
                        .rotationEffect(.degrees(-20))
                    Text("HortaHub")
                        .font(.system(size: 32, weight: .bold))
                        .foregroundColor(.white)
                    Spacer()
                }
                .padding(.horizontal, 28)
                .padding(.top, 60)
                .padding(.bottom, 28)

                // Sheet with carousel
                VStack(spacing: 0) {
                    Spacer().frame(height: 28)

                    Text("Escolha seu plano")
                        .font(.system(size: 22, weight: .bold))
                        .foregroundColor(.hortaDarkGreen)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.horizontal, 24)

                    Spacer().frame(height: 20)

                    // Carousel
                    TabView(selection: $selectedIndex) {
                        ForEach(Array(hortaPlans.enumerated()), id: \.offset) { index, plan in
                            PlanCardView(plan: plan)
                                .padding(.horizontal, 20)
                                .tag(index)
                        }
                    }
                    .tabViewStyle(.page(indexDisplayMode: .never))
                    .frame(height: 360)

                    // Page indicator dots
                    HStack(spacing: 8) {
                        ForEach(0..<hortaPlans.count, id: \.self) { i in
                            Circle()
                                .fill(i == selectedIndex ? Color.hortaDarkGreen : Color.gray.opacity(0.3))
                                .frame(width: i == selectedIndex ? 8 : 6, height: i == selectedIndex ? 8 : 6)
                                .animation(.spring(response: 0.3), value: selectedIndex)
                        }
                    }
                    .padding(.top, 12)

                    // Swipe hint
                    HStack(spacing: 6) {
                        Image(systemName: "arrow.left")
                            .font(.system(size: 11))
                        Text("passe para os lados")
                            .font(.system(size: 12))
                        Image(systemName: "arrow.right")
                            .font(.system(size: 11))
                    }
                    .foregroundColor(.gray)
                    .padding(.top, 10)

                    Spacer().frame(height: 16)

                    Text("Os itens podem ser selecionados semanalmente pelo assinante,\nrespeitando os limites do plano e a disponibilidade da safra.")
                        .font(.system(size: 11))
                        .foregroundColor(.gray)
                        .multilineTextAlignment(.center)
                        .padding(.horizontal, 28)
                        .padding(.bottom, 32)
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .background(
                    Color.hortaSheet
                        .clipShape(RoundedRectangle(cornerRadius: 32, style: .continuous))
                )
            }
        }
    }
}

// MARK: - Plan Card
struct PlanCardView: View {
    let plan: Plan

    var body: some View {
        VStack(spacing: 0) {
            // Card content
            VStack(alignment: .leading, spacing: 0) {
                // Plan name
                Text(plan.name)
                    .font(.system(size: 17, weight: .semibold))
                    .foregroundColor(.hortaDarkGreen)
                    .frame(maxWidth: .infinity, alignment: .center)
                    .padding(.top, 20)

                // Price
                HStack(alignment: .lastTextBaseline, spacing: 0) {
                    Text("R$")
                        .font(.system(size: 14, weight: .semibold))
                        .foregroundColor(.hortaDarkGreen)
                    Text("\(plan.price)")
                        .font(.system(size: 42, weight: .bold))
                        .foregroundColor(.hortaDarkGreen)
                    Text("/ mês")
                        .font(.system(size: 13))
                        .foregroundColor(.gray)
                        .padding(.leading, 2)
                }
                .frame(maxWidth: .infinity, alignment: .center)
                .padding(.top, 6)

                // Items list
                VStack(alignment: .leading, spacing: 7) {
                    ForEach(plan.items, id: \.self) { item in
                        HStack(spacing: 8) {
                            Circle()
                                .fill(Color.hortaDarkGreen.opacity(0.6))
                                .frame(width: 5, height: 5)
                            Text(item)
                                .font(.system(size: 14))
                                .foregroundColor(.hortaDarkGreen.opacity(0.85))
                        }
                    }
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.horizontal, 24)
                .padding(.top, 16)

                Spacer()

                // CTA button
                Button {
                    // select plan
                } label: {
                    Text(plan.buttonLabel)
                        .font(.system(size: 15, weight: .semibold))
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity)
                        .frame(height: 48)
                        .background(Color.hortaDarkGreen)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                }
                .padding(.horizontal, 16)
                .padding(.bottom, 18)
            }
            .frame(maxWidth: .infinity, minHeight: 310)
            .background(Color.white.opacity(0.6))
            .clipShape(RoundedRectangle(cornerRadius: 20, style: .continuous))
            .overlay(
                RoundedRectangle(cornerRadius: 20, style: .continuous)
                    .strokeBorder(Color.hortaLightGreen.opacity(0.25), lineWidth: 1)
            )
            .shadow(color: .black.opacity(0.06), radius: 8, y: 3)
        }
    }
}

// MARK: - Previews

#Preview("Landing") {
    LandingView().environmentObject(AppRouter())
}

#Preview("Phone Input") {
    PhoneInputView().environmentObject(AppRouter())
}

#Preview("Verification") {
    VerificationView().environmentObject(AppRouter())
}

#Preview("Plans") {
    PlansView().environmentObject(AppRouter())
}

#Preview("Full Flow") {
    ContentView()
}
