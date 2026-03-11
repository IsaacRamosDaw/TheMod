import { Ads } from "../components/Ads"
import "../styles/components/homeBody.css"
export function HomeBody() {
    return (
        <main className="home-body">
          <div className="ads-container">
            <Ads src="/ads/premium-ad.svg" p="" link="" />
          </div>
          <div className="ads-container">
            <Ads src="/ads/premium-ad.svg" p="" link="" />
          </div>
        </main>
    )
}