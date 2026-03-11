export const Ads = ({p, src, link}) => {
    return (
        <div className="ads">
            <img src={src} alt="" />
          <p>{p}</p>
          <a href={link}></a>
        </div>
    )
}